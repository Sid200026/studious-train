package com.pro.deepak.com.update.mRecycler;

public class fieldsData  {

    String desc,deadline,subject;

    public fieldsData() {}

    public  fieldsData(String desc,String deadline,String subject){
        this.desc =desc;
        this.deadline = deadline;
        this.subject = subject;
    }

    public String getDesc() { return desc; }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
