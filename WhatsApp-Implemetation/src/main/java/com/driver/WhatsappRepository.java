package com.driver;

import java.util.*;

import org.springframework.stereotype.Repository;

@Repository
public class WhatsappRepository {

    //Assume that each user belongs to at most one group
    //You can use the below mentioned hashmaps or delete these and create your own.
    private HashMap<Group, List<User>> groupUserMap;
    private HashMap<Group, List<Message>> groupMessageMap;
    private HashMap<Message, User> senderMap;
    private HashMap<Group, User> adminMap;
    private HashSet<String> userMobile;

    private int customGroupCount;
    private int messageId;

    public WhatsappRepository(){
        this.groupMessageMap = new HashMap<Group, List<Message>>();
        this.groupUserMap = new HashMap<Group, List<User>>();
        this.senderMap = new HashMap<Message, User>();
        this.adminMap = new HashMap<Group, User>();
        this.userMobile = new HashSet<>();
        this.customGroupCount = 0;
        this.messageId = 0;
    }


    public String createUser(String name, String mobile) throws Exception{
        if(userMobile.contains(mobile)){
            throw new Exception("User already exists");
        }
        User user = new User(name,mobile);
        userMobile.add(mobile);
        return "SUCCESS";
    }

    public Group createGroup(List<User> users) {

        String groupName = "";

        if(users.size()==2){              // personal chat
            groupName = users.get(1).getName();
        }else{                              // group
            this.customGroupCount++;
            groupName = "Group "+ this.customGroupCount;
        }

        Group group = new Group(groupName ,users.size());
        this.groupUserMap.put(group,users);
        this.adminMap.put(group,users.get(0));
        this.groupMessageMap.put(group,new ArrayList<>());

        return group;
    }

    public int createMessage(String content) {
        messageId++;
        Message message = new Message(messageId,content);
        return messageId;
    }

    public int sendMessage(Message message, User sender, Group group) throws Exception{
        if(!groupUserMap.containsKey(group))  throw new Exception("Group does not exist");

        if(!groupUserMap.get(group).contains(sender)) throw new Exception("You are not allowed to send message");

        List<Message> list = groupMessageMap.get(group);
        list.add(message);
        groupMessageMap.put(group,list);
        senderMap.put(message,sender);
        return list.size();

    }

    public String changeAdmin(User approver, User user, Group group) throws Exception{
        if(!groupUserMap.containsKey(group))    throw new Exception("Group does not exist");

        if(!adminMap.get(group).equals(approver))   throw new Exception( "Approver does not have rights");

        if(!groupUserMap.get(group).contains(user)) throw new Exception("User is not a participant");

        adminMap.put(group,user);
        return "SUCCESS";

    }

    public int removeUser(User user) throws Exception{
        boolean isPresent = false;
        Group gp = null;

        // remove from groupUserMap, userMobile
        for(Group group: groupUserMap.keySet()){
            if (groupUserMap.get(group).contains(user)) {
                groupUserMap.get(group).remove(user);
                userMobile.remove(user.getMobile());
                gp = group;
                isPresent = true;
            }
        }
        if(!isPresent) throw new Exception("User not found");

        // check for admin
        for(User u: adminMap.values()){
            if (u.equals(user)) {
                throw new Exception("Cannot remove admin");
            }
        }

        // remove user's messages from senderMap, groupMessageMap, messageMap
        for(Message m: senderMap.keySet()){
            if(senderMap.get(m).equals(user)){
                senderMap.remove(m);
                groupMessageMap.get(gp).remove(m);
            }
        }

        return groupUserMap.get(gp).size() + groupMessageMap.get(gp).size() + senderMap.size();

    }

    public String findMessage(Date start, Date end, int k) throws Exception{
        Map<Integer,String> map = new TreeMap();
        for(Message m: senderMap.keySet()){
            if(start.compareTo(m.getTimestamp())<0 && m.getTimestamp().compareTo(end)<0){
                map.put(m.getId(),m.getContent());
            }
        }
        if(map.size()<k) throw new Exception("K is greater than the number of messages");

        List<String> list = new ArrayList<>(map.values());
        return list.get(list.size()-k);
    }
}
