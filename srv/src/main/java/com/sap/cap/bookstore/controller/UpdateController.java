package com.sap.cap.bookstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;




@RestController
@RequestMapping("/v1/oil")
public class UpdateController {
    private static Connection con;

    private static String result;

    private static ResultSet resultSetConnectionDetails, resultSetGetUsersInOrg, resultSetGetGroup,
    resultSetGetUserGrps;
    private static Statement statementConnectionDetail, statementGetUsersInOrg, statementGetAppInScenario,
    statementUpdateConDetail, statementSelectGroupName, statementGetGroup;

    List<String> actualGroups, groupNames;


    @GetMapping("/AssignUsscUserGroups")
    public String assignUserGroup(){
        System.out.println("666");
        try {
            con = DriverManager.getConnection("jdbc:sqlite:/home/user/projects/bookstore/sqlite.db");
            statementConnectionDetail=con.createStatement();
            // resultSetConnectionDetails = statementConnectionDetail.executeQuery(
            //                     "SELECT PNID,CONNECTIONTYPE_ID,SCENARIO_ID from ICH_CONNECTION_MANAGEMENT_CONNECTIONDETAIL  "
            //                             + "WHERE ISCONNECTIONTYPECHANGED = 'false';");
            resultSetConnectionDetails = statementConnectionDetail.executeQuery(
                                "SELECT PNID,CONNECTIONTYPE_ID,SCENARIO_ID from ICH_CONNECTION_MANAGEMENT_CONNECTIONDETAIL  "
                                        + "WHERE ISCONNECTIONTYPECHANGED = 'false' AND CONNECTIONTYPE_ID IN (3,4,5);");
            while (resultSetConnectionDetails.next()) {
                System.out.println("pnid"+ resultSetConnectionDetails.getString("PNID"));
                String pnid=resultSetConnectionDetails.getString("PNID");
                String scenerio_id=resultSetConnectionDetails.getString("SCENARIO_ID");

                statementGetUsersInOrg = con.createStatement();
                resultSetGetUsersInOrg = statementGetUsersInOrg.executeQuery(
                        "SELECT ID,PNID,TYPE_ID,NAMEID,FIRSTNAME,LASTNAME,EMAIL from ICH_CONNECTION_MANAGEMENT_USER WHERE PNID = '"
                                + pnid + "';");

                while(resultSetGetUsersInOrg.next()){
                    // System.out.println("resultSetGetUsersInOrg = " + resultSetGetUsersInOrg.getString("ID"));
                    actualGroups = new ArrayList<String>();
                    groupNames = new ArrayList<String>();

                    String userid = resultSetGetUsersInOrg.getString("ID");
                    String nameid = resultSetGetUsersInOrg.getString("NAMEID");

                    resultSetGetUserGrps = getUserGroup(userid);

                    while (resultSetGetUserGrps.next()) {
                        System.out.println("Before delete resultSetGetUserGrps= "
                                + resultSetGetUserGrps.getString("NAME"));
                        actualGroups.add(resultSetGetUserGrps.getString("ID"));
                    }

                    statementGetGroup = con.createStatement();
                    resultSetGetGroup = statementGetGroup.executeQuery(
                            "SELECT * from ICH_CONNECTION_MANAGEMENT_GROUP WHERE SCENARIO_ID = '"
                                    + scenerio_id + "';");

                    while (resultSetGetGroup.next()) {
                        // System.out.println("the group id is "
                                // + resultSetGetGroup.getString("ID"));
                        
                        if(!actualGroups.isEmpty()){
                            if(actualGroups.contains(resultSetGetGroup.getString("ID"))){
                                result+= "\n the last consequence id is "
                                + resultSetGetUsersInOrg.getString("ID")+" "
                                + resultSetGetUsersInOrg.getString("PNID")+" "
                                + resultSetGetUsersInOrg.getString("FIRSTNAME")+" "
                                + resultSetGetUsersInOrg.getString("LASTNAME")+" "
                                + resultSetGetUsersInOrg.getString("EMAIL")+" "
                                + resultSetGetGroup.getString("ID")+" "
                                + resultSetGetGroup.getString("NAME")+" "
                                + resultSetGetGroup.getString("DESCRIPTION")+" "
                                + resultSetGetGroup.getString("SCENARIO_ID")+" "
                                + resultSetGetGroup.getString("ROLE");
                            }
                        }
                        
                    }

                }

                

            }
            
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        
        return result;
    }

    private ResultSet getUserGroup(String userid) {
        try {
            // System.err.println("Join start");
            statementSelectGroupName = con.createStatement();
            return statementSelectGroupName.executeQuery(
                    "SELECT ID,NAME from ICH_CONNECTION_MANAGEMENT_GROUP as grp INNER JOIN ICH_CONNECTION_MANAGEMENT_USERGROUPMAPPING as usrgrp ON usrgrp.GRP_ID = grp.ID WHERE usrgrp.USER_ID = '"
                            + userid + "';");
        } catch (SQLException e) {
            System.err.println("Join Query failed! e = " + e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
