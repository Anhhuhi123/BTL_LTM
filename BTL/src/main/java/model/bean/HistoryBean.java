package model.bean;

public class HistoryBean {
	private int id;
	private int userId;

	private String filePdf;
    private String fileDocx;
    private String date;

    public HistoryBean() {}

    public HistoryBean(int userId,String filePdf, String fileDocx) {
    	this.userId = userId;
        this.filePdf = filePdf;
        this.fileDocx = fileDocx;
    }

    public HistoryBean(int id,int userId,String filePdf, String fileDocx,String date) {
    	this.id = id;
    	this.userId = userId;
        this.filePdf = filePdf;
        this.fileDocx = fileDocx;
        this.date = date;
    }

    // Getter và Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFilePdf() {
        return filePdf;
    }

    public void setFilePdf(String filePdf) {
        this.filePdf = filePdf;
    }

    public String getFileDocx() {
        return fileDocx;
    }

    public void setFileDocx(String fileDocx) {
        this.fileDocx = fileDocx;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}


