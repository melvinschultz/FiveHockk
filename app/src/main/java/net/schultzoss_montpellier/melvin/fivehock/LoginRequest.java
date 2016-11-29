package net.schultzoss_montpellier.melvin.fivehock;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by melvin on 29/11/16.
 */

public class LoginRequest extends StringRequest {

    private final static String LOGIN_REQUEST_URL = "http://melvinschultz.fr/fivehock/Login.php";
    private Map<String, String> params;

    public LoginRequest(String email, String password, Response.Listener<String> listener) {
        super(Request.Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("email", email);
        params.put("password", password);

        System.out.println(email);
        System.out.println(password);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
