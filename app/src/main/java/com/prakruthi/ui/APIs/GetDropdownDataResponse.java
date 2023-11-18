package com.prakruthi.ui.APIs;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetDropdownDataResponse {


    @SerializedName("status_code")
    @Expose
    private Boolean statusCode;
    @SerializedName("departments")
    @Expose
    private List<Department> departments;
    @SerializedName("city")
    @Expose
    private List<City> city;
    @SerializedName("state")
    @Expose
    private List<State> state;
    @SerializedName("district")
    @Expose
    private List<District> district;

    public class Department {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("priority")
        @Expose
        private Integer priority;
        @SerializedName("logged_date")
        @Expose
        private String loggedDate;
        @SerializedName("created_by")
        @Expose
        private Integer createdBy;
        @SerializedName("updated_date")
        @Expose
        private String updatedDate;
        @SerializedName("updated_by")
        @Expose
        private String updatedBy;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getPriority() {
            return priority;
        }

        public void setPriority(Integer priority) {
            this.priority = priority;
        }

        public String getLoggedDate() {
            return loggedDate;
        }

        public void setLoggedDate(String loggedDate) {
            this.loggedDate = loggedDate;
        }

        public Integer getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(Integer createdBy) {
            this.createdBy = createdBy;
        }

        public String getUpdatedDate() {
            return updatedDate;
        }

        public void setUpdatedDate(String updatedDate) {
            this.updatedDate = updatedDate;
        }

        public String getUpdatedBy() {
            return updatedBy;
        }

        public void setUpdatedBy(String updatedBy) {
            this.updatedBy = updatedBy;
        }
    }

    public class State {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("attachment")
        @Expose
        private String attachment;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("cgst")
        @Expose
        private String cgst;
        @SerializedName("sgst")
        @Expose
        private String sgst;
        @SerializedName("igst")
        @Expose
        private String igst;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("created_by")
        @Expose
        private Integer createdBy;
        @SerializedName("logged_date")
        @Expose
        private String loggedDate;
        @SerializedName("updated_by")
        @Expose
        private String updatedBy;
        @SerializedName("updated_date")
        @Expose
        private String updatedDate;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAttachment() {
            return attachment;
        }

        public void setAttachment(String attachment) {
            this.attachment = attachment;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getCgst() {
            return cgst;
        }

        public void setCgst(String cgst) {
            this.cgst = cgst;
        }

        public String getSgst() {
            return sgst;
        }

        public void setSgst(String sgst) {
            this.sgst = sgst;
        }

        public String getIgst() {
            return igst;
        }

        public void setIgst(String igst) {
            this.igst = igst;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Integer getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(Integer createdBy) {
            this.createdBy = createdBy;
        }

        public String getLoggedDate() {
            return loggedDate;
        }

        public void setLoggedDate(String loggedDate) {
            this.loggedDate = loggedDate;
        }

        public String getUpdatedBy() {
            return updatedBy;
        }

        public void setUpdatedBy(String updatedBy) {
            this.updatedBy = updatedBy;
        }

        public String getUpdatedDate() {
            return updatedDate;
        }

        public void setUpdatedDate(String updatedDate) {
            this.updatedDate = updatedDate;
        }
    }

    public class District {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("state_id")
        @Expose
        private Integer stateId;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("attachment")
        @Expose
        private String attachment;
        @SerializedName("pincode")
        @Expose
        private String pincode;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("priority")
        @Expose
        private String priority;
        @SerializedName("log_date_created")
        @Expose
        private String logDateCreated;
        @SerializedName("created_by")
        @Expose
        private Integer createdBy;
        @SerializedName("log_date_modified")
        @Expose
        private String logDateModified;
        @SerializedName("modified_by")
        @Expose
        private String modifiedBy;
        @SerializedName("state_name")
        @Expose
        private String stateName;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getStateId() {
            return stateId;
        }

        public void setStateId(Integer stateId) {
            this.stateId = stateId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getAttachment() {
            return attachment;
        }

        public void setAttachment(String attachment) {
            this.attachment = attachment;
        }

        public String getPincode() {
            return pincode;
        }

        public void setPincode(String pincode) {
            this.pincode = pincode;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getPriority() {
            return priority;
        }

        public void setPriority(String priority) {
            this.priority = priority;
        }

        public String getLogDateCreated() {
            return logDateCreated;
        }

        public void setLogDateCreated(String logDateCreated) {
            this.logDateCreated = logDateCreated;
        }

        public Integer getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(Integer createdBy) {
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

        public String getStateName() {
            return stateName;
        }

        public void setStateName(String stateName) {
            this.stateName = stateName;
        }
    }


    public class City {
        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("district_id")
        @Expose
        private Integer districtId;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("attachment")
        @Expose
        private String attachment;
        @SerializedName("pincode")
        @Expose
        private String pincode;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("priority")
        @Expose
        private String priority;
        @SerializedName("log_date_created")
        @Expose
        private String logDateCreated;
        @SerializedName("created_by")
        @Expose
        private Integer createdBy;
        @SerializedName("log_date_modified")
        @Expose
        private String logDateModified;
        @SerializedName("modified_by")
        @Expose
        private String modifiedBy;


        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getDistrictId() {
            return districtId;
        }

        public void setDistrictId(Integer districtId) {
            this.districtId = districtId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getAttachment() {
            return attachment;
        }

        public void setAttachment(String attachment) {
            this.attachment = attachment;
        }

        public String getPincode() {
            return pincode;
        }

        public void setPincode(String pincode) {
            this.pincode = pincode;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getPriority() {
            return priority;
        }

        public void setPriority(String priority) {
            this.priority = priority;
        }

        public String getLogDateCreated() {
            return logDateCreated;
        }

        public void setLogDateCreated(String logDateCreated) {
            this.logDateCreated = logDateCreated;
        }

        public Integer getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(Integer createdBy) {
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
    }


}
