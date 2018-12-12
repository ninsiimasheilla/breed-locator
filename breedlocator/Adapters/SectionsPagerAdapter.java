package com.example.richardsenyange.breedlocator.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.richardsenyange.breedlocator.Fragaments.Breed_search;
import com.example.richardsenyange.breedlocator.Fragaments.Talk_to_vet;
import com.example.richardsenyange.breedlocator.Fragaments.chats;


public class SectionsPagerAdapter extends FragmentPagerAdapter {


    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch(position) {

            case 0:
                Breed_search breed_search = new Breed_search();
                return breed_search;

            case 1:
                chats Chats = new chats();
                return  Chats;

            case 2:
                Talk_to_vet talk_to_vet = new Talk_to_vet();
                return talk_to_vet;

            default:
                return  null;
        }

    }

    @Override
    public int getCount() {
        return 3;
    }

    public CharSequence getPageTitle(int position){

        switch (position) {

            case 0:
                return "Breed Search";

            case 1:
                return "Chats";

            case 2:
                return "Talk To Vet";

            default:
                return null;
        }

    }

}
