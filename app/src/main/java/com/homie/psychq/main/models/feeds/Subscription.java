package com.homie.psychq.main.models.feeds;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/*Model For Subscription Transactions With API*/

public class Subscription {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("user_email")
    @Expose
    private String user_email;

    @SerializedName("order_id")
    @Expose
    private String order_id;

    @SerializedName("purchase_token")
    @Expose
    private String purchase_token;

    @SerializedName("sku_product")
    @Expose
    private String sku_product;

    @SerializedName("package_name")
    @Expose
    private String package_name;

    @SerializedName("developer_payload")
    @Expose
    private String developer_payload;

    @SerializedName("original_json")
    @Expose
    private String original_json;

    @SerializedName("purchase_status")
    @Expose
    private String purchase_status;


    @SerializedName("purchase_time")
    @Expose
    private String purchase_time;

    @SerializedName("is_acknowledged")
    @Expose
    private boolean is_acknowledged;

    @SerializedName("is_auto_renewing")
    @Expose
    private boolean is_auto_renewing;

    @SerializedName("purchase_signature")
    @Expose
    private String purchase_signature;

    @SerializedName("is_active")
    @Expose
    private boolean is_active;


    public Subscription() {
    }

    public Subscription(String id, String user_email, String order_id, String purchase_token, String sku_product, String package_name, String developer_payload, String original_json, String purchase_status, String purchase_time, boolean is_acknowledged, boolean is_auto_renewing, String purchase_signature, boolean is_active) {
        this.id = id;
        this.user_email = user_email;
        this.order_id = order_id;
        this.purchase_token = purchase_token;
        this.sku_product = sku_product;
        this.package_name = package_name;
        this.developer_payload = developer_payload;
        this.original_json = original_json;
        this.purchase_status = purchase_status;
        this.purchase_time = purchase_time;
        this.is_acknowledged = is_acknowledged;
        this.is_auto_renewing = is_auto_renewing;
        this.purchase_signature = purchase_signature;
        this.is_active = is_active;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getPurchase_token() {
        return purchase_token;
    }

    public void setPurchase_token(String purchase_token) {
        this.purchase_token = purchase_token;
    }

    public String getSku_product() {
        return sku_product;
    }

    public void setSku_product(String sku_product) {
        this.sku_product = sku_product;
    }

    public String getPackage_name() {
        return package_name;
    }

    public void setPackage_name(String package_name) {
        this.package_name = package_name;
    }

    public String getDeveloper_payload() {
        return developer_payload;
    }

    public void setDeveloper_payload(String developer_payload) {
        this.developer_payload = developer_payload;
    }

    public String getOriginal_json() {
        return original_json;
    }

    public void setOriginal_json(String original_json) {
        this.original_json = original_json;
    }

    public String getPurchase_status() {
        return purchase_status;
    }

    public void setPurchase_status(String purchase_status) {
        this.purchase_status = purchase_status;
    }

    public String getPurchase_time() {
        return purchase_time;
    }

    public void setPurchase_time(String purchase_time) {
        this.purchase_time = purchase_time;
    }

    public boolean isIs_acknowledged() {
        return is_acknowledged;
    }

    public void setIs_acknowledged(boolean is_acknowledged) {
        this.is_acknowledged = is_acknowledged;
    }

    public boolean isIs_auto_renewing() {
        return is_auto_renewing;
    }

    public void setIs_auto_renewing(boolean is_auto_renewing) {
        this.is_auto_renewing = is_auto_renewing;
    }

    public String getPurchase_signature() {
        return purchase_signature;
    }

    public void setPurchase_signature(String purchase_signature) {
        this.purchase_signature = purchase_signature;
    }

    public boolean isIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    @Override
    public String toString() {
        return "Subscription{" +
                "id='" + id + '\'' +
                ", user_email='" + user_email + '\'' +
                ", order_id='" + order_id + '\'' +
                ", purchase_token='" + purchase_token + '\'' +
                ", sku_product='" + sku_product + '\'' +
                ", package_name='" + package_name + '\'' +
                ", developer_payload='" + developer_payload + '\'' +
                ", original_json='" + original_json + '\'' +
                ", purchase_status='" + purchase_status + '\'' +
                ", purchase_time='" + purchase_time + '\'' +
                ", is_acknowledged=" + is_acknowledged +
                ", is_auto_renewing=" + is_auto_renewing +
                ", purchase_signature='" + purchase_signature + '\'' +
                ", is_active=" + is_active +
                '}';
    }
}
