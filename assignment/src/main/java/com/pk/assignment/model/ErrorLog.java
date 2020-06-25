package com.pk.assignment.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ERROR_LOG")
public class ErrorLog {

        @Id
        @GeneratedValue(strategy=GenerationType.AUTO)
        private Long id;
        
        @Column(name = "ERROR_TYPE")
        private String errorType;
        
        @Column(name ="ERROR_DESCRIPTION ")
        private String errorDiscription;
                
        @Column(name ="PAYLOAD")
        private String payload;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getErrorType() {
            return errorType;
        }

        public void setErrorType(String errorType) {
            this.errorType = errorType;
        }

        public String getErrorDiscription() {
            return errorDiscription;
        }

        public void setErrorDiscription(String errorDiscription) {
            this.errorDiscription = errorDiscription;
        }

        public String getPayload() {
            return payload;
        }

        public void setPayload(String payload) {
            this.payload = payload;
        }
        
        
}
