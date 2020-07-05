package com.pk.assignment.Beans;

import java.util.Objects;
import javax.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

/**
 * ErrorResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen",
        date = "2020-07-02T20:19:21.104Z")

public class ErrorResponse {
    @JsonProperty("status")
    private String status = null;

    @JsonProperty("message")
    private String message = null;

    @JsonProperty("error_type")
    private String errorType = null;

    public ErrorResponse status(String status) {
        this.status = status;
        return this;
    }

    /**
     * The status code.
     * 
     * @return status
     **/
    @ApiModelProperty(required = true, value = "The status code.")
    @NotNull


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ErrorResponse message(String message) {
        this.message = message;
        return this;
    }

    /**
     * error message
     * 
     * @return message
     **/
    @ApiModelProperty(required = true, value = "error message")
    @NotNull


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ErrorResponse errorType(String errorType) {
        this.errorType = errorType;
        return this;
    }

    /**
     * type of exception
     * 
     * @return errorType
     **/
    @ApiModelProperty(value = "type of exception")


    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ErrorResponse errorResponse = (ErrorResponse) o;
        return Objects.equals(this.status, errorResponse.status)
                && Objects.equals(this.message, errorResponse.message)
                && Objects.equals(this.errorType, errorResponse.errorType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, message, errorType);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ErrorResponse {\n");

        sb.append("    status: ").append(toIndentedString(status)).append("\n");
        sb.append("    message: ").append(toIndentedString(message)).append("\n");
        sb.append("    errorType: ").append(toIndentedString(errorType)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces (except the first
     * line).
     */
    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}

