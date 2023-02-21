package pro.lrn.two_endpoints.service;
import org.springframework.stereotype.Service;
import pro.lrn.two_endpoints.dto.InformationAboutServiceProviderDTO;
import pro.lrn.two_endpoints.dto.PaymentLinkDTO;
import pro.lrn.two_endpoints.dto.PaymentLinkRequestDTO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class PaymentLinkService {
    public PaymentLinkDTO parsePaymentLink(PaymentLinkRequestDTO request) {
        PaymentLinkDTO dto = new PaymentLinkDTO();
        try {
            String paymentLink = request.getPaymentLink();
            if (paymentLink == null) {
                throw new NullPointerException("Payment link is null");
            }
            if (!paymentLink.startsWith("https://")) {
                throw new IllegalArgumentException("Invalid payment link");
            }


            String[] parts = paymentLink.split("#");
            if (parts.length != 2) {
                throw new IllegalArgumentException("Invalid payment link");
            }

            String data = parts[1];
            Map<String, List<String>> values = new HashMap<>();

            while (data.length() > 0) {
                String id = data.substring(0, 2);
                int length = Integer.parseInt(data.substring(2, 4));
                String value = data.substring(4, 4 + length);

                if (values.containsKey(id)) {
                    values.get(id).add(value);
                } else {
                    List<String> list = new ArrayList<>();
                    list.add(value);
                    values.put(id, list);
                }

                data = data.substring(4 + length);
            }

            dto.setStandardVersion(values.get("00").get(0));
            dto.setTypeOfPaymentLink(values.get("01").get(0));
            dto.setMcc(values.get("52").get(0));
            dto.setCurrencyCode(values.get("53").get(0));
            List<String> paymentAmountList = values.get("54");
            if (paymentAmountList != null && !paymentAmountList.isEmpty()) {
                dto.setPaymentAmount(paymentAmountList.get(0));
            } else {
                dto.setPaymentAmount(null);
            }

            dto.setProviderName(values.get("59").get(0));
            dto.setDataChecksum(values.get("63").get(0));

            InformationAboutServiceProviderDTO infos = new InformationAboutServiceProviderDTO();
            Map<String, Map<String, String>> info2 = new HashMap<>();
            for (int i = 32; i <= 51; i++) {
                if (values.containsKey(String.format("%02d", i))) {
                    List<String> valuesList = values.get(String.format("%02d", i));
                    if (valuesList != null) {
                        for (String value : valuesList) {
                            Map<String, String> info_values = new HashMap<>();
                            while (value.length() > 0) {
                                String id = value.substring(0, 2);
                                int info_length = Integer.parseInt(value.substring(2, 4));
                                String info_value = value.substring(4, 4 + info_length);
                                info_values.put(id, info_value);
                                info2.put(String.format("%02d", i), info_values);
                                value = value.substring(4 + info_length);
                            }
                            infos.setInfo(info2);
                            dto.setInformationAboutServiceProviderDTO(infos);

                        }
                    }
                }
            }


        } catch (Exception e) {
            dto.setError("Error while parsing: " + e.getMessage());
        }
        return dto;
    }


    public String createPaymentLink(PaymentLinkDTO paymentDTO) throws InvalidPaymentLinkException {
        StringBuilder createdLink = new StringBuilder("https://balance.kg/#");


        if (paymentDTO.getStandardVersion() != null && !paymentDTO.getStandardVersion().isEmpty()) {
            if (!paymentDTO.getStandardVersion().equals("01")) {
                throw new InvalidPaymentLinkException("Invalid Standard Version");}
        } else {
            paymentDTO.setStandardVersion("01");
        }
        createdLink.append("00").append("02").append(paymentDTO.getStandardVersion());



        if (paymentDTO.getTypeOfPaymentLink() != null && !paymentDTO.getTypeOfPaymentLink().isEmpty()) {
            if (paymentDTO.getTypeOfPaymentLink().equals("11") || paymentDTO.getTypeOfPaymentLink().equals("12"))  {
                createdLink.append("01").append("02").append(paymentDTO.getTypeOfPaymentLink());
            } else {
                throw new InvalidPaymentLinkException("Invalid Type of Payment Link");
            }
        }

        Map<String, Map<String, String>> infos = new HashMap<>();
        infos = paymentDTO.getInformationAboutServiceProviderDTO().getInfo();
        Map<String, String> nested_info = new HashMap<>();
        for (Map.Entry<String, Map<String, String>> entry: infos.entrySet()){
            nested_info = entry.getValue();
            String id = entry.getKey();
            StringBuilder totalString = new StringBuilder();
            for (Map.Entry<String, String> nested_entry: nested_info.entrySet()) {
                String nested_id = nested_entry.getKey();
                String data = nested_entry.getValue();
                totalString.append(nested_id).append(String.format("%02d",data.length())).append(data);
            }
            createdLink.append(id).append(String.format("%02d", totalString.length())).append(totalString);

        }

        createdLink.append("52").append("04").append(paymentDTO.getMcc());

        createdLink.append("53").append("03");
        if (paymentDTO.getCurrencyCode() != null && !paymentDTO.getCurrencyCode().isEmpty()) {
            createdLink.append(paymentDTO.getCurrencyCode());
        } else {
            createdLink.append("417");
        }

        if (paymentDTO.getPaymentAmount() != null && !paymentDTO.getPaymentAmount().isEmpty()) {
            if (paymentDTO.getPaymentAmount().length() > 13) {
                throw new InvalidPaymentLinkException("Length of Payment Amount should be between 1 and 13");
            }
            createdLink.append("54").append(String.format("%02d", paymentDTO.getPaymentAmount().length())).append(paymentDTO.getPaymentAmount());
        }

        int length_of_name = paymentDTO.getProviderName().length();
        if (length_of_name < 1 || length_of_name > 25) {
            throw new InvalidPaymentLinkException("Length of Provider Name should be between 1 and 25");
        }
        createdLink.append("59").append(String.format("%02d", length_of_name)).append(paymentDTO.getProviderName());

        createdLink.append("63").append("04").append(paymentDTO.getDataChecksum());

        return createdLink.toString();
    }
}
