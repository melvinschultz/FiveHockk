package net.schultzoss_montpellier.melvin.fivehock;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by melvin on 22/11/16.
 */

public class RegisterRequest extends StringRequest {

    private final static String REGISTER_REQUEST_URL = "http://melvinschultz.fr/fivehock/Register.php";
    private Map<String, String> params;

    public RegisterRequest(String username, String email, String password, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("username", username);
        params.put("email", email);
        params.put("password", password);

//        System.out.println(username);
//        System.out.println(email);
//        System.out.println(password);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
