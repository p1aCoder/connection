namespace ich.connection.management;

entity Group {
    key ID : String(100);
    name: String(50);
    description : String(300);
    scenario_id : Integer;
    visibility : String(100);
    role : String(15)
}

entity User {
    key ID : String(50);
    UID: String(5000);
    NAMEID: String(5000);
    SPUSERID: String(5000);
    PNID: String(5000);
    FIRSTNAME: String(32);
    LASTNAME: String(64);
    EMAIL: String(5000);
    STATUS: String(5000);
    TYPE_ID: Integer;
    AUTHENTICATION_ID: String(5000);
    ACTIVATEDAT: Timestamp;
    CREATEDAT: Timestamp;
    CREATEDBYOBT: Boolean;
    CHANGEDAT: Timestamp;
    ORGANIZATIONTYPE: String(5000);
    INVITINGMAHPNID: String(5000);
    ORG_ID: String(5000);
    CREATEDBY: String(5000);

}

entity UserGroupMapping {
    key User_id : String(500);
    key Grp_id: String(500);
}


entity ConnectionDetail {
    key pnid : String(30);
    CONNECTIONTYPE_ID : Integer;
    RECEIVERADAPTERTYPE_ID : Integer;
    RECEIVERURL : String(1000);
    AUTHENTICATIONTYPE : String(20);
    CREDENTIALKEYSTOREALIAS : String(200);
    CREDENTIALUSERNAME : String(200);
    CREDENTIALENCODED: String(1000);
    TESTCONNECTIONSUCCESSFUL: Boolean;
    ISMESSAGEVERIFIED: Boolean;
    key SCENARIO_ID : Integer;
    TPI_PNID : String(20);
    AS2ID: String(100);
    ISCONNECTIONTYPECHANGED: Boolean;
    CHANGEDBY: String(5000); 
}