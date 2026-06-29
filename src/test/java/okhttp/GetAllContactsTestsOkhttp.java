package okhttp;

import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class GetAllContactsTestsOkhttp {
    String token = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoidGVzdHVzZXJAdGVzdC5jb20iLCJpc3MiOiJSZWd1bGFpdCIsImV4cCI6MTc4MzExMjM2NiwiaWF0IjoxNzgyNTEyMzY2fQ.PY2zvSiG9lMRyf1G8UikDvNx2QHL85dRHrMdLNGP6_A";
    Gson gson = new Gson();
    OkHttpClient client = new OkHttpClient();

    @Test
    public void getAllContactSuccessTest() throws IOException {
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts")
                .header("authorization", token)
                .build();

        Response response = client.newCall(request).execute();
        System.out.println("code: " + response.code());
        Assert.assertTrue(response.isSuccessful());
        Assert.assertEquals(response.code(), 200);
    }

    @Test
    public void getAllContactUnauthorizedTest() throws IOException {
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts")
                .build();

        Response response = client.newCall(request).execute();
        System.out.println("code: " + response.code());
        Assert.assertFalse(response.isSuccessful());
        Assert.assertEquals(response.code(), 403);
    }
}
