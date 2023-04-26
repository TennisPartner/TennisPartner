package com.tennisPartner.tennisP.common.Exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomException extends RuntimeException {
        private String message;
        private int status;
        public CustomException(String message, int status)
        {
            this.message = message;
            this.status = status;

        }


}
