package com.gadjetmart.service.soap;

import com.gadjetmart.wsdl.GetAllProductsRequest;
import com.gadjetmart.wsdl.GetAllProductsResponse;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

@Component
public class ProductClient extends WebServiceGatewaySupport {

    public GetAllProductsResponse getAllAbansProducts(String url) {
        try {
            GetAllProductsRequest request =  new GetAllProductsRequest();
            GetAllProductsResponse response = (GetAllProductsResponse) getWebServiceTemplate().marshalSendAndReceive(request, new SoapActionCallback(url));
            return response;
        } catch (Exception e) {
            return null;
        }
    }
}
