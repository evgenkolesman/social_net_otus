import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import io.restassured.RestAssured.given
import io.restassured.response.ValidatableResponse
import org.springframework.http.MediaType
import org.springframework.stereotype.Component


@Component
class ContainerControllerMethods(private val objectMapper: ObjectMapper) {


    fun getRequestToController(path: String): ValidatableResponse {
        return getRequestToController(path, true)
    }

    fun getRequestToController(path: String, needEncoding: Boolean): ValidatableResponse {
        return ThreadLocal.withInitial {
            given().contentType(MediaType.APPLICATION_JSON_VALUE)
                .urlEncodingEnabled(needEncoding)
                .log().all()
                .`when`()
                .get(path)
                .then().and().log().all()
        }.get()
    }

    @Throws(JsonProcessingException::class)
    fun <T> putRequestToController(path: String?, body: T): ValidatableResponse {
        val newBody = objectMapper.writeValueAsString(body)
        return ThreadLocal.withInitial {
            given().contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(newBody)
                .log().all()
                .`when`()
                .log().all()
                .put(path)
                .then().and().log().all()
        }.get()
    }

    @Throws(JsonProcessingException::class)
    fun <T> postRequestToController(path: String, body: T): ValidatableResponse {
        val newBody = objectMapper.writeValueAsString(body)
        return ThreadLocal.withInitial {
            given().contentType(MediaType.APPLICATION_JSON_VALUE)
                .log().all()
                .body(newBody)
                .`when`()
                .post(path)
                .then().and().log().all()
        }.get()
    }

    fun deleteRequestToController(path: String?): ValidatableResponse {
        return ThreadLocal.withInitial {
            given().contentType(MediaType.APPLICATION_JSON_VALUE)
                .log().all()
                .`when`()
                .delete(path)
                .then().and().log().all()
        }.get()
    }
}