package com.edeadlock.openid;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.openid4java.association.AssociationException;
import org.openid4java.consumer.ConsumerException;
import org.openid4java.consumer.ConsumerManager;
import org.openid4java.consumer.VerificationResult;
import org.openid4java.discovery.DiscoveryException;
import org.openid4java.discovery.DiscoveryInformation;
import org.openid4java.discovery.Identifier;
import org.openid4java.message.AuthRequest;
import org.openid4java.message.AuthSuccess;
import org.openid4java.message.MessageException;
import org.openid4java.message.ParameterList;
import org.openid4java.message.ax.AxMessage;
import org.openid4java.message.ax.FetchRequest;
import org.openid4java.message.ax.FetchResponse;

public class DiscoverEndpoint {
	
	public static ConsumerManager manager;

    public DiscoverEndpoint() throws ConsumerException {
    	manager = new ConsumerManager();
    }

	/**
	 * @param args
	 * @throws DiscoveryException 
	 * @throws ConsumerException 
	 * @throws MessageException 
	 */
	public static String getAuthUrl(String url, HttpServletRequest request) {
		
		String userSuppliedString = url;
		String _returnUrl = "https://pusher.edeadlock.com/return.jsp";
		DiscoverEndpoint end;
		try {
			end = new DiscoverEndpoint();
		
		
		// perform discovery on the user-supplied identifier
	    List discoveries = end.manager.discover(userSuppliedString);

	    // attempt to associate with the OpenID provider
	    // and retrieve one service endpoint for authentication
	    DiscoveryInformation discovered = end.manager.associate(discoveries);
	    
	    // store the discovery information in the user's session for later use
	    // leave out for stateless operation / if there is no session
	    request.getSession().setAttribute("discovered", discovered);


	    // obtain a AuthRequest message to be sent to the OpenID provider
	    AuthRequest authReq = end.manager.authenticate(discovered, _returnUrl);
	    // Attribute Exchange example: fetching the 'email' attribute
        FetchRequest fetch = FetchRequest.createFetchRequest();
        fetch.addAttribute("name",
                // attribute alias
                "http://axschema.org/namePerson",   // type URI
                true);                                      // required
        fetch.addAttribute("email",
                // attribute alias
                "http://axschema.org/contact/email",   // type URI
                true);
        // attach the extension to the authentication request
        authReq.addExtension(fetch);

	    
	    return authReq.getDestinationUrl(true);
	    
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	public static String verify(HttpServletRequest request) {
		 // extract the parameters from the authentication response
	    // (which comes in as a HTTP request from the OpenID provider)
	    ParameterList openidResp = new ParameterList(request.getParameterMap());

	    // retrieve the previously stored discovery information
	    DiscoveryInformation discovered = (DiscoveryInformation) request.getSession().getAttribute("discovered");

	    // extract the receiving URL from the HTTP request
	    String receivingURL = "https://" + request.getServerName() +  request.getRequestURI() + "?" + request.getQueryString();
	    request.getSession().setAttribute("receivingURL", receivingURL);
	    request.getSession().setAttribute("openid.identity", request.getParameter("openid.identity"));

	    // verify the response
	    VerificationResult verification;
		try {
			verification = manager.verify(receivingURL.toString(), openidResp, discovered);
			// examine the verification result and extract the verified identifier
		    Identifier verified = verification.getVerifiedId();

		    if (verified != null) {
		    	AuthSuccess authSuccess =
                        (AuthSuccess) verification.getAuthResponse();

                if (authSuccess.hasExtension(AxMessage.OPENID_NS_AX))
                {
                	FetchResponse fetchResp = (FetchResponse) authSuccess
                            .getExtension(AxMessage.OPENID_NS_AX);
                    List emails = fetchResp.getAttributeValues("email");
                    request.getSession().setAttribute("email", emails.get(0).toString());
                    return emails.get(0).toString();     
                }

		    	
		    }
		} catch (MessageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DiscoveryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AssociationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	    return null;
	}
	
	public static void main(String args[]) {
		DiscoverEndpoint.getAuthUrl("https://logon-uat.onenetwork.com/sso/auth/id?mpericharla", null);
	}

}
