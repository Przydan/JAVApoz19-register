package pl.sda.register.repository;

import org.springframework.stereotype.Repository;
import pl.sda.register.exception.DuplicateUserException;
import pl.sda.register.exception.UserNotFoundException;
import pl.sda.register.model.User;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class UserRepository {

    private Set<User> users = initializeUsers();

    private Set<User> initializeUsers() {
        return new HashSet<>(Arrays.asList(new User("login", "Captain", "Jack")));
    }

    public Set<String> findAllUserNames(String firstName, boolean matchExact) {
        return Optional.ofNullable(firstName)
                .map(name -> users.stream()
                        .filter(firstNamePredicate(name, matchExact))
                        .map(User::getUsername)
                        .collect(Collectors.toSet()))
                .orElse(users.stream().map(User::getUsername).collect(Collectors.toSet()));

    }

    private Predicate<User> firstNamePredicate(String name, boolean matchExact) {
        return matchExact ?
                user -> user.getFirstName().equals(name) :
                user -> user.getFirstName().contains(name);
    }

    public User findUserByUsername(String username) {
        return users.stream()
                .filter(user -> user.getUsername().equals(username))
                .findAny()
                .orElseThrow(() -> new UserNotFoundException("User with username: " + username + " not found"));
    }

    public void addUser(User user) {
//FIX: problem with jetty and java 11
//        users.stream()
//                .filter(existingUser -> existingUser.getUsername()
//                        .equals(user.getUsername()))
//                .findAny()
//                .ifPresentOrElse(
//                        u -> throwDuplicatedUserNameException(u.getUsername()),
//                        () -> users.add(user));
        users.stream()
                .filter(existingUser -> existingUser.getUsername()
                        .equals(user.getUsername()))
                .findAny()
                .ifPresent(
                        u -> throwDuplicatedUserNameException(u.getUsername()));
        users.add(user);
    }

    private void throwDuplicatedUserNameException(String username) {
        throw new DuplicateUserException("User " + username + " already exist");
    }

    public void removeUser(String username) {
        users.remove(findUserByUsername(username));
    }

    public void updateUser(User user) {
        User userToUpdate = findUserByUsername(user.getUsername());
        users.remove(userToUpdate);
        users.add(user);
    }
}
