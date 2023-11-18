package com.prakruthi.ui.ui.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfileGetUserDataRetrofit {

    @SerializedName("status_code")
    @Expose
    private Boolean statusCode;
    @SerializedName("privacyPolicy")
    @Expose
    private String privacyPolicy;
    @SerializedName("termsAndConditions")
    @Expose
    private String termsAndConditions;
    @SerializedName("returnRefundPolicy.")
    @Expose
    private String returnRefundPolicy;
    @SerializedName("security")
    @Expose
    private String security;
    @SerializedName("aboutUs")
    @Expose
    private String aboutUs;
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("base_path")
    @Expose
    private String base_path;


    @SerializedName("data")
    @Expose
    private Data data;



    public Boolean getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Boolean statusCode) {
        this.statusCode = statusCode;
    }

    public String getPrivacyPolicy() {
        return privacyPolicy;
    }

    public void setPrivacyPolicy(String privacyPolicy) {
        this.privacyPolicy = privacyPolicy;
    }

    public String getTermsAndConditions() {
        return termsAndConditions;
    }

    public void setTermsAndConditions(String termsAndConditions) {
        this.termsAndConditions = termsAndConditions;
    }

    public String getReturnRefundPolicy() {
        return returnRefundPolicy;
    }

    public void setReturnRefundPolicy(String returnRefundPolicy) {
        this.returnRefundPolicy = returnRefundPolicy;
    }

    public String getSecurity() {
        return security;
    }

    public void setSecurity(String security) {
        this.security = security;
    }

    public String getAboutUs() {
        return aboutUs;
    }

    public void setAboutUs(String aboutUs) {
        this.aboutUs = aboutUs;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getBase_path() {
        return base_path;
    }

    public void setBase_path(String base_path) {
        this.base_path = base_path;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("department_id")
        @Expose
        private Integer departmentId;
        @SerializedName("user_code")
        @Expose
        private String userCode;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("last_name")
        @Expose
        private String lastName;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("password")
        @Expose
        private String password;
        @SerializedName("mobile")
        @Expose
        private String mobile;
        @SerializedName("gender")
        @Expose
        private String gender;
        @SerializedName("dob")
        @Expose
        private String dob;
        @SerializedName("attachment")
        @Expose
        private String attachment;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("postCode")
        @Expose
        private String postCode;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("state")
        @Expose
        private String state;
        @SerializedName("country")
        @Expose
        private String country;
        @SerializedName("district")
        @Expose
        private String district;
        @SerializedName("street")
        @Expose
        private String street;
        @SerializedName("about")
        @Expose
        private String about;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("mobile_verified")
        @Expose
        private String mobileVerified;
        @SerializedName("is_verified")
        @Expose
        private String isVerified;
        @SerializedName("log_date_created")
        @Expose
        private String logDateCreated;
        @SerializedName("created_by")
        @Expose
        private String createdBy;
        @SerializedName("log_date_modified")
        @Expose
        private String logDateModified;
        @SerializedName("modified_by")
        @Expose
        private String modifiedBy;
        @SerializedName("fcm_token")
        @Expose
        private String fcmToken;
        @SerializedName("device_id")
        @Expose
        private String deviceId;
        @SerializedName("allow_email")
        @Expose
        private String allowEmail;
        @SerializedName("allow_sms")
        @Expose
        private String allowSms;
        @SerializedName("allow_push")
        @Expose
        private String allowPush;
        @SerializedName("department_name")
        @Expose
        private String departmentName;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getDepartmentId() {
            return departmentId;
        }

        public void setDepartmentId(Integer departmentId) {
            this.departmentId = departmentId;
        }

        public String getUserCode() {
            return userCode;
        }

        public void setUserCode(String userCode) {
            this.userCode = userCode;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getDob() {
            return dob;
        }

        public void setDob(String dob) {
            this.dob = dob;
        }

        public String getAttachment() {
            return attachment;
        }

        public void setAttachment(String attachment) {
            this.attachment = attachment;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getPostCode() {
            return postCode;
        }

        public void setPostCode(String postCode) {
            this.postCode = postCode;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getAbout() {
            return about;
        }

        public void setAbout(String about) {
            this.about = about;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMobileVerified() {
            return mobileVerified;
        }

        public void setMobileVerified(String mobileVerified) {
            this.mobileVerified = mobileVerified;
        }

        public String getIsVerified() {
            return isVerified;
        }

        public void setIsVerified(String isVerified) {
            this.isVerified = isVerified;
        }

        public String getLogDateCreated() {
            return logDateCreated;
        }

        public void setLogDateCreated(String logDateCreated) {
            this.logDateCreated = logDateCreated;
        }

        public String getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
        }

        public String getLogDateModified() {
            return logDateModified;
        }

        public void setLogDateModified(String logDateModified) {
            this.logDateModified = logDateModified;
        }

        public String getModifiedBy() {
            return modifiedBy;
        }

        public void setModifiedBy(String modifiedBy) {
            this.modifiedBy = modifiedBy;
        }

        public String getFcmToken() {
            return fcmToken;
        }

        public void setFcmToken(String fcmToken) {
            this.fcmToken = fcmToken;
        }

        public String getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        public String getAllowEmail() {
            return allowEmail;
        }

        public void setAllowEmail(String allowEmail) {
            this.allowEmail = allowEmail;
        }

        public String getAllowSms() {
            return allowSms;
        }

        public void setAllowSms(String allowSms) {
            this.allowSms = allowSms;
        }

        public String getAllowPush() {
            return allowPush;
        }

        public void setAllowPush(String allowPush) {
            this.allowPush = allowPush;
        }

        public String getDepartmentName() {
            return departmentName;
        }

        public void setDepartmentName(String departmentName) {
            this.departmentName = departmentName;
        }
    }


}
