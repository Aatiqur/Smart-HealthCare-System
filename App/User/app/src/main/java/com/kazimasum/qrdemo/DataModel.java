package com.kazimasum.qrdemo;

public class DataModel
{
    String HospitalName,AvailableBeds,TotalBeds;
    DataModel()
    {
    }

    public DataModel(String hospitalName, String availableBeds, String totalBeds) {
        HospitalName = hospitalName;
        AvailableBeds = availableBeds;
        TotalBeds = totalBeds;
    }

    public String getHospitalName() {

        return HospitalName;
    }

    public void setHospitalName(String hospitalName)
    {
        HospitalName = hospitalName;
    }

    public String getAvailableBeds()
    {
        return AvailableBeds;
    }

    public void setAvailableBeds(String availableBeds)
    {
        AvailableBeds = availableBeds;
    }

    public String getTotalBeds()
    {
        return TotalBeds;
    }

    public void setTotalBeds(String totalBeds)
    {
        TotalBeds = totalBeds;
    }
}
