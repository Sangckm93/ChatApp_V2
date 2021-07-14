package com.gmail.sangckm93.superchat.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gmail.sangckm93.superchat.Adapter.UserAdapter;
import com.gmail.sangckm93.superchat.MainActivity;
import com.gmail.sangckm93.superchat.Model.Chat;
import com.gmail.sangckm93.superchat.Model.Chatlist;
import com.gmail.sangckm93.superchat.Model.User;
import com.gmail.sangckm93.superchat.Notifications.Token;
import com.gmail.sangckm93.superchat.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.List;


public class ChatsFragment extends Fragment {

    private RecyclerView recyclerView;

    private UserAdapter userAdapter;
    private List<User> mUsers;

    FirebaseUser fuser;
    DatabaseReference reference;

    private List<Chatlist> usersList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_chats, container, false);

       recyclerView = view.findViewById(R.id.recycler_view);
       recyclerView.setHasFixedSize(true);
       recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

       fuser = FirebaseAuth.getInstance().getCurrentUser();

       usersList = new ArrayList<>();

//       reference = FirebaseDatabase.getInstance().getReference("chats");
//       reference.addValueEventListener(new ValueEventListener() {
//           @Override
//           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//               usersList.clear();
//
//               for (DataSnapshot snapshot : dataSnapshot.getChildren()){
//                   Chat chat = snapshot.getValue(Chat.class);
//
//                   if (chat.getSender().equals(fuser.getUid())){
//                       usersList.add(chat.getReceiver());
//                   }
//
//                   if (chat.getReceiver().equals(fuser.getUid())){
//                       usersList.add(chat.getSender());
//                   }
//               }
//               readChats();
//           }
//
//           @Override
//           public void onCancelled(@NonNull DatabaseError error) {
//
//           }
//       });

        reference = FirebaseDatabase.getInstance().getReference("Chatlist").child(fuser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usersList.clear();
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Chatlist chatlist = snapshot.getValue(Chatlist.class);
                    usersList.add(chatlist);
                }
                chatList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        updateToken(FirebaseInstanceId.getInstance().getToken());
        return view;
    }

    private void updateToken(String token){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tokens");
        Token token1 = new Token(token);
        reference.child(fuser.getUid()).setValue(token1);
    }

    private void chatList() {
        mUsers = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUsers.clear();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    User user = snapshot.getValue(User.class);
                    for (Chatlist chatlist: usersList){
                        if (user.getId().equals(chatlist.getId())){
                            mUsers.add(user);
                        }
                    }
                }
                userAdapter = new UserAdapter(getContext(), mUsers, true);
                recyclerView.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

//    private void readChats(){
//
//        mUsers = new ArrayList<>();
//
//        reference = FirebaseDatabase.getInstance().getReference("Users");
//
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                mUsers.clear();
//
//                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
//                    User user = snapshot.getValue(User.class);
//                     //Display 1 User from chats
//                    for (String id: usersList){
//                        if (user.getId().equals(id)){
//                            if (mUsers.size()!= 0){
//                                boolean check = true;
//                                for (int cnt =0; cnt<mUsers.size(); cnt++){
//                                    if (user.getId().equals(mUsers.get(cnt).getId())) {
//                                        check = false;
//                                        cnt = mUsers.size();
//                                    }
//                                }
//                                if (check) mUsers.add(user);
//
////                                for (User user1 : mUsers){
////                                    if (!user.getId().equals(user1.getId())){
////                                        mUsers.add(user);
////                                    }
////                                }
//                            }else{
//                                mUsers.add(user);
//                            }
//                        }
//                    }
//                }
//                userAdapter = new UserAdapter(getContext(), mUsers, true);
//                recyclerView.setAdapter(userAdapter);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }
}
