package okhttp;

import com.google.gson.Gson;
import dto.AuthRequestDto;
import dto.AuthResponseDto;
import dto.ErrorDto;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Random;

public class RegistrationTestsOkhttp {
    Gson gson = new Gson();
    public static final MediaType JSON = MediaType.get("application/json");
    OkHttpClient client = new OkHttpClient();

    @Test
    public void registrationSuccessTest() throws IOException {
        Random random = new Random();
        int i = random.nextInt(10000);
        System.out.println(i);
        AuthRequestDto auth = AuthRequestDto.builder()
                .username("testuser" + i + "@test.com")
                .password("9Brm_@$b<(Hx6!(t3q~BoMo?a`7")
                .build();

        RequestBody body = RequestBody.create(gson.toJson(auth), JSON);
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/user/registration/usernamepassword")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        Assert.assertTrue(response.isSuccessful());
        Assert.assertEquals(response.code(), 200);

        String responseBody = response.body().string();
        AuthResponseDto responseDto = gson.fromJson(responseBody, AuthResponseDto.class);
        System.out.println(responseDto.getToken());
    }

    @Test
    public void registrationInvalidEmailTest() throws IOException {

        AuthRequestDto auth = AuthRequestDto.builder()
                .username("testusertest.com")
                .password("9Brm_@$b<(Hx6!(t3q~BoMo?a`7")
                .build();

        RequestBody body = RequestBody.create(gson.toJson(auth), JSON);
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/user/registration/usernamepassword")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        Assert.assertFalse(response.isSuccessful());
        Assert.assertEquals(response.code(), 400);



        ErrorDto errorDto = gson.fromJson(response.body().string(), ErrorDto.class);
        Assert.assertEquals(errorDto.getStatus(), 400);
        System.out.println(errorDto.getMessage().toString());
        Assert.assertTrue(errorDto.getMessage().toString().contains("username=must be a well-formed email address"));

        Assert.assertEquals(errorDto.getPath(), "/v1/user/registration/usernamepassword");
    }


}
