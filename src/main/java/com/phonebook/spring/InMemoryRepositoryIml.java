package com.phonebook.spring;

import com.phonebook.main.InMemoryRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * Keeps phoneBook data in memory in ordered in accordance to addition.
 */
@Repository
public class InMemoryRepositoryIml implements InMemoryRepository {

    private Map<String, Set<String>> data;

    /**
     * no args constructor
     */
    public InMemoryRepositoryIml() {
        // LinkedHashMap is chosen because usually iteration order matters
        this(new LinkedHashMap<>());
    }

    /**
     * this constructor allows to inject initial data to the repository
     *
     * @param data
     */
    public InMemoryRepositoryIml(Map<String, Set<String>> data) {
        this.data = new LinkedHashMap<>(data);
    }

    @Override
    public Map<String, Set<String>> findAll() {
        return new LinkedHashMap<>(this.data);
    }

    @Override
    public Set<String> findAllPhonesByName(String name) {
        return data.get(name);
    }

    @Override
    public String findNameByPhone(String phone) {
        List<Set<String>> phones = new ArrayList<>(data.values());
        List<String> names = new ArrayList<>(data.keySet());
        for (int i = 0; i < phones.size(); i++) {
            if (phones.get(i).contains(phone)) {
                return names.get(i);
            }
        }
        return "No phone number in phone book";
    }

    @Override
    public void addPhone(String name, String phone) {
        String[] phoneArray = phone.split(",");
        data.put(name, new HashSet<>(Arrays.asList(phoneArray)));
    }

    @Override
    public void removePhone(String phone) throws IllegalArgumentException {
        String name = findNameByPhone(phone);
        if (name.equals("No phone number in phone book")) {
            throw new IllegalArgumentException();
        }
        findAllPhonesByName(findNameByPhone(phone)).remove(phone);
       
        if (findAllPhonesByName(name).size() == 0) {
            data.remove(name);
        }
    }
}
