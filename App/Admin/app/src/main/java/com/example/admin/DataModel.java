package com.example.admin;

public class DataModel
{
    String HospitalUID,BedNumber;

    public DataModel(String hospitalUID, String bedNumber)
    {
        HospitalUID = hospitalUID;
        BedNumber = bedNumber;
    }
    DataModel()
    {
    }

    public String getHospitalUID()
    {
        return HospitalUID;
    }

    public String getBedNumber()
    {
        return BedNumber;
    }

    public void setHospitalUID(String hospitalUID) {
        HospitalUID = hospitalUID;
    }

    public void setBedNumber(String bedNumber) {
        BedNumber = bedNumber;
    }







}
