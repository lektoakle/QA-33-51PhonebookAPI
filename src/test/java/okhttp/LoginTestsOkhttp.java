package okhttp;

import com.google.gson.Gson;
import dto.AuthRequestDto;
import dto.AuthResponseDto;
import dto.ErrorDto;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class LoginTestsOkhttp {
    Gson gson = new Gson();
    public static final MediaType JSON = MediaType.get("application/json");
    OkHttpClient client = new OkHttpClient();

    @Test
    public void LoginSuccessTest() throws IOException {
        AuthRequestDto auth = AuthRequestDto.builder()
                .username("testuser@test.com")
                .password("aaA1234#")
                .build();

        RequestBody body = RequestBody.create(gson.toJson(auth), JSON);
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/user/login/usernamepassword")
                .post(body)
                .build();


        Response response = client.newCall(request).execute();
        System.out.println("code: " + response.code());
        Assert.assertTrue(response.isSuccessful());
        Assert.assertEquals(response.code(), 200);

        String responseBody = response.body().string();
        AuthResponseDto responseDto = gson.fromJson(responseBody, AuthResponseDto.class);
        System.out.println(responseDto.getToken());
    }

    @Test
    public void LoginWrongEmailNoShtrudelTest() throws IOException {
        AuthRequestDto auth = AuthRequestDto.builder()
                .username("testusertest.com")
                .password("aaA1234#")
                .build();

        RequestBody body = RequestBody.create(gson.toJson(auth), JSON);
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/user/login/usernamepassword")
                .post(body)
                .build();


        Response response = client.newCall(request).execute();
        System.out.println("code: " + response.code());
        Assert.assertFalse(response.isSuccessful());
        Assert.assertEquals(response.code(), 400);


        ErrorDto errorDto = gson.fromJson(response.body().string(),ErrorDto.class);
        Assert.assertEquals(errorDto.getStatus(),401);
        Assert.assertEquals(errorDto.getMessage(), "Login or Password incorrect");
        Assert.assertEquals(errorDto.getPath(),"/v1/user/login/usernamepassword");
    }

    @Test
    public void LoginWrongPasswordTest() throws IOException {
        AuthRequestDto auth = AuthRequestDto.builder()
                .username("testuser@test.com")
                .password("aaA1234a")
                .build();

        RequestBody body = RequestBody.create(gson.toJson(auth), JSON);
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/user/login/usernamepassword")
                .post(body)
                .build();


        Response response = client.newCall(request).execute();
        System.out.println("code: " + response.code());
        Assert.assertFalse(response.isSuccessful());
        Assert.assertEquals(response.code(), 401);

        ErrorDto errorDto = gson.fromJson(response.body().string(), ErrorDto.class);
        Assert.assertEquals(errorDto.getStatus(),401);
        Assert.assertEquals(errorDto.getMessage(), "Login or Password incorrect");
        Assert.assertEquals(errorDto.getPath(),"/v1/user/login/usernamepassword");

    }

}
