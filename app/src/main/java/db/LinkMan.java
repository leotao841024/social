package db;

import java.util.Map;

/**
 * Created by jam on 2016/9/23.
 */
public class LinkMan extends DBEntity<LinkMan> {
//    id,EntranceID,ResidentName,PhoneNumber,SequenceNumber,ResidentType,Email
    private String EntranceID,ResidentName,PhoneNumber,ResidentType,Email;
    private int SequenceNumber;

    public String getEntranceID() {
        return EntranceID;
    }

    public void setEntranceID(String entranceID) {
        EntranceID = entranceID;
    }

    public String getResidentName() {
        return ResidentName;
    }

    public void setResidentName(String residentName) {
        ResidentName = residentName;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getResidentType() {
        return ResidentType;
    }

    public void setResidentType(String residentType) {
        ResidentType = residentType;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public int getSequenceNumber() {
        return SequenceNumber;
    }

    public void setSequenceNumber(int sequenceNumber) {
        SequenceNumber = sequenceNumber;
    }

    @Override
    protected String getTableNm() {
        return null;
    }

    @Override
    protected String[] getTableColumn() {
        return new String[]{"EntranceID","ResidentName","PhoneNumber","SequenceNumber","ResidentType","Email"};
    }

    @Override
    protected Object[] getTableColumnValues() {
        return new Object[]{EntranceID,ResidentName,PhoneNumber,SequenceNumber,ResidentType,Email};
    }

    @Override
    protected Class<?>[] getTableColumnTypes() {
        return new Class<?>[]{String.class,String.class,String.class,Integer.class,String.class,String.class};
    }

    @Override
    protected void build(LinkMan entry, Map<String, Object> values) {
        entry.setId((Long) values.get("id"));
        entry.setEmail((String) values.get("Email"));
        entry.setEntranceID((String) values.get("EntranceID"));
        entry.setPhoneNumber((String) values.get("PhoneNumber"));
        entry.setResidentName((String) values.get("ResidentName"));
        entry.setResidentType((String) values.get("ResidentType"));
        entry.setSequenceNumber((Integer)values.get("SequenceNumber"));
    }
}
