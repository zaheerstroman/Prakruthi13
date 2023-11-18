package com.prakruthi.ui.ui.profile;

public class ProfileGetUserDataResponse {

    private Boolean statusCode;
    private String privacyPolicy;
    private String termsAndConditions;

    private String returnRefundPolicy;

    private String security;

    private String aboutUs;
    private String message;

    private String base_path;


    private ProfileGetUserDataModel data;

    public ProfileGetUserDataModel getData() {
        return data;
    }

    public void setData(ProfileGetUserDataModel data) {
        this.data = data;
    }



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



    public ProfileGetUserDataResponse(String privacyPolicy, String termsAndConditions, String returnRefundPolicy, String security, String aboutUs, String message, String base_path) {
//        this.statusCode = this.statusCode;
        this.privacyPolicy = privacyPolicy;
        this.termsAndConditions = termsAndConditions;
        this.returnRefundPolicy = returnRefundPolicy;
        this.security = security;
        this.aboutUs = aboutUs;
        this.message = message;
        this.base_path=base_path;
        this.data = data;
    }


    public static class ProfileGetUserDataModel {

        private String id;

        private String departmentId;


        private String userCode;
        private String name;
        private String lastName;
        private String email;


        private String password;
        private String mobile;
        private String gender;
        private String dob;
        private String attachment;
        private String city;
        private String postCode;
        private String address;
        private String state;
        private String country;
        private String district;
        private String street;
        private String about;
        private String status;
        private String mobileVerified;
        private String isVerified;
        private String logDateCreated;
        private String createdBy;
        private String logDateModified;
        private String modifiedBy;
        private String fcmToken;
        private String deviceId;
        private String allowEmail;
        private String allowSms;
        private String allowPush;
        private String departmentName;

        public ProfileGetUserDataModel(String id, String departmentId, String userCode, String name, String lastName,
                                       String email, String password, String mobile, String gender, String dob,
                                       String attachment, String city, String postCode, String address, String state,
                                       String country, String district, String street, String about, String status,
                                       String mobileVerified, String isVerified, String logDateCreated, String createdBy,
                                       String logDateModified, String modifiedBy, String fcmToken, String deviceId,
                                       String allowEmail, String allowSms, String allowPush, String departmentName) {


            this.id = id;
            this.departmentId = departmentId;
            this.userCode = userCode;
            this.name = name;
            this.lastName = lastName;

            this.email = email;

            this.password = password;
            this.mobile = mobile;
            this.gender = gender;
            this.dob = dob;
            this.attachment = attachment;
            this.city = city;
            this.postCode = postCode;
            this.address = address;
            this.state = state;
            this.country = country;
            this.district = district;
            this.street = street;
            this.about = about;
            this.status = status;
            this.mobileVerified = mobileVerified;
            this.isVerified = isVerified;
            this.logDateCreated = logDateCreated;
            this.createdBy = createdBy;
            this.logDateModified = logDateModified;
            this.modifiedBy = modifiedBy;
            this.fcmToken = fcmToken;
            this.deviceId = deviceId;
            this.allowEmail = allowEmail;
            this.allowSms = allowSms;
            this.allowPush = allowPush;

            this.departmentName = departmentName;


        }


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDepartmentId() {
            return departmentId;
        }

        public void setDepartmentId(String departmentId) {
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
