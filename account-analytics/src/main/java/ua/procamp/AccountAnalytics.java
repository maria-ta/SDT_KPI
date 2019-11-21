package ua.procamp;

import ua.procamp.exception.EntityNotFoundException;
import ua.procamp.model.Account;
import ua.procamp.model.Sex;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.time.Month;
import java.util.*;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.*;

/**
 * Implement methods using Stream API
 */
public class AccountAnalytics {
    private Collection<Account> accounts;

    public static AccountAnalytics of(Collection<Account> accounts) {
        return new AccountAnalytics(accounts);
    }

    private AccountAnalytics(Collection<Account> accounts) {
        this.accounts = accounts;
    }

    /**
     * Returns {@link Optional} that contains an {@link Account} with the max value of balance
     *
     * @return account with max balance wrapped with optional
     */
    public Optional<Account> findRichestPerson() {
        /*
        Comparator<Account> comparator = new Comparator<Account>(){
            public int compare(Account a, Account b) {
                return a.getBalance().compareTo(b.getBalance());
            }
        };
        return Optional.of(Collections.max(accounts, comparator));
         */
        return accounts.stream().max(comparing(Account::getBalance));
    }

    /**
     * Returns a {@link List} of {@link Account} that have a birthday month equal to provided.
     *
     * @param birthdayMonth a month of birth
     * @return a list of accounts
     */
    public List<Account> findAccountsByBirthdayMonth(Month birthdayMonth) {
        /*
        ArrayList<Account> filtered = new ArrayList<Account>();
        for (Account elem : accounts){
            if (elem.getBirthday().getMonth() == birthdayMonth){
                filtered.add(elem);
            }
        }
        return filtered;
         */
        return accounts.stream().filter(a -> a.getBirthday().getMonth().equals(birthdayMonth)).collect(toList());
    }

    /**
     * Returns a map that separates all accounts into two lists - male and female. Map has two keys {@code true} indicates
     * male list, and {@code false} indicates female list.
     *
     * @return a map where key is true or false, and value is list of male, and female accounts
     */
    public Map<Boolean, List<Account>> partitionMaleAccounts() {
        /*
        Map<Boolean, List<Account>> map = new HashMap<Boolean, List<Account>>();
        map.put(true, new ArrayList<Account>());
        map.put(false, new ArrayList<Account>());
        for (Account elem : accounts){
            if (elem.getSex().equals(Sex.MALE)){
                map.get(true).add(elem);
            }
            else map.get(false).add(elem);
        }
        return map;
         */
        return accounts.stream().collect(groupingBy(a -> a.getSex().equals(Sex.MALE)));
    }

    /**
     * Returns a {@link Map} that stores accounts grouped by its email domain. A map key is {@link String} which is an
     * email domain like "gmail.com". And the value is a {@link List} of {@link Account} objects with a specific email domain.
     *
     * @return a map where key is an email domain and value is a list of all account with such email
     */
    public Map<String, List<Account>> groupAccountsByEmailDomain() {
        /*
        Collection<String> domains = new HashSet<String>();
        Map<String, List<Account>> map = new HashMap<String, List<Account>>();
        for (Account elem : accounts){
            domains.add(elem.getEmail().split("@")[1]);
        }
        for (String domain: domains){
            map.put(domain, new ArrayList<Account>());
        }
        for (Account elem : accounts){
            map.get(elem.getEmail().split("@")[1]).add(elem);
        }
        return map;

         */
        return accounts.stream().collect(groupingBy(a -> a.getEmail().split("@")[1]));
    }

    /**
     * Returns a number of letters in all first and last names.
     *
     * @return total number of letters of first and last names of all accounts
     */
    public int getNumOfLettersInFirstAndLastNames() {
        /*
        int sum = 0;
        for (Account elem : accounts){
            sum += elem.getFirstname().length();
            sum += elem.getLastname().length();
        }
        return sum;
        */
        return accounts.stream().mapToInt(a -> a.getFirstname().length() + a.getLastname().length()).sum();
    }

    /**
     * Returns a total balance of all accounts.
     *
     * @return total balance of all accounts
     */
    public BigDecimal calculateTotalBalance() {
        /*
        BigDecimal sum = BigDecimal.valueOf(0);
        for (Account elem : accounts){
           sum = sum.add(elem.getBalance());
        }
        return sum;*/
        return accounts.stream().map(Account::getBalance).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Returns a {@link List} of {@link Account} objects sorted by first and last names.
     *
     * @return list of accounts sorted by first and last names
     */
    public List<Account> sortByFirstAndLastNames() {
        /*
        Comparator<Account> comparator = new Comparator<Account>(){
            public int compare(Account a, Account b) {
                String aFirstname = a.getFirstname();
                String bFirstname = b.getFirstname();
                String aLastname = a.getLastname();
                String bLastname = b.getLastname();
                int sComp = aFirstname.compareTo(bFirstname);
                if (sComp != 0) {
                    return sComp;
                }
                return aLastname.compareTo(bLastname);
            }
        };
        ArrayList<Account> sorted = new ArrayList<>(accounts);
        sorted.sort(comparator);
        return sorted;
         */
        return accounts.stream().sorted(comparing(Account::getFirstname).thenComparing(Account::getLastname)).collect(toList());
    }

    /**
     * Checks if there is at least one account with provided email domain.
     *
     * @param emailDomain
     * @return true if there is an account that has an email with provided domain
     */
    public boolean containsAccountWithEmailDomain(String emailDomain) {
        /*
        for (Account elem : accounts){
            if (elem.getEmail().split("@")[1].equals(emailDomain)) return true;
        }
        return false;
         */
        return accounts.stream().map(Account::getEmail).anyMatch(email -> email.split("@")[1].equals(emailDomain));
    }

    /**
     * Returns account balance by its email. Throws {@link EntityNotFoundException} with message
     * "Cannot find Account by email={email}" if account is not found.
     *
     * @param email account email
     * @return account balance
     */
    public BigDecimal getBalanceByEmail(String email) {
        /*
        for (Account elem : accounts){
            if (elem.getEmail().equals(email)) return elem.getBalance();
        }
        throw new EntityNotFoundException("Cannot find Account by email="+email);
         */
        return accounts.stream().filter(account -> account.getEmail().equals(email)).findFirst().map(Account::getBalance)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Cannot find Account by email=%s", email)));
    }

    /**
     * Collects all existing accounts into a {@link Map} where a key is account id, and the value is {@link Account} instance
     *
     * @return map of accounts by its ids
     */
    public Map<Long, Account> collectAccountsById() {
        /*
        Map<Long, Account> map = new HashMap<Long, Account>();
        for (Account elem : accounts){
            map.put(elem.getId(), elem);
        }
        return map;
         */
        return accounts.stream().collect(toMap(Account::getId, identity()));
    }

    /**
     * Filters accounts by the year when an account was created. Collects account balances by its emails into a {@link Map}.
     * The key is {@link Account#email} and the value is {@link Account#balance}
     *
     * @param year the year of account creation
     * @return map of account by its ids the were created in a particular year
     */
    public Map<String, BigDecimal> collectBalancesByIdForAccountsCreatedOn(int year) {
        /*
        Map<String, BigDecimal> map = new HashMap<String, BigDecimal>();
        for (Account elem : accounts){
            if (elem.getCreationDate().getYear() == year) map.put(elem.getEmail(), elem.getBalance());
        }
        return map;*/
        return accounts.stream().filter(account -> account.getCreationDate().getYear() == year)
                .collect(toMap(Account::getEmail, Account::getBalance));
    }

    /**
     * Returns a {@link Map} where key is {@link Account#lastName} and values is a {@link Set} that contains first names
     * of all accounts with a specific last name.
     *
     * @return a map where key is a first name and value is a set of first names
     */
    public Map<String, Set<String>> groupFirstNamesByLastNames() {
        /*
        Map<String, Set<String>> map = new HashMap<String, Set<String>>();
        HashSet<String> lastnames = new HashSet<String>();
        for (Account elem : accounts){
            lastnames.add(elem.getLastname());
        }
        for (String lastname : lastnames){
            map.put(lastname, new HashSet<String>());
        }
        for (Account elem : accounts){
            map.get(elem.getLastname()).add(elem.getFirstname());
        }
        return map;
         */
        return accounts.stream().collect(groupingBy(Account::getLastname, mapping(Account::getFirstname, toSet())));
    }

    /**
     * Returns a {@link Map} where key is a birthday month, and value is a {@link String} that stores comma and space
     * -separated first names (e.g. "Polly, Dylan, Clark"), of all accounts that have the same birthday month.
     *
     * @return a map where a key is a birthday month and value is comma-separated first names
     */
    public Map<Month, String> groupCommaSeparatedFirstNamesByBirthdayMonth() {
        /*
        Map<Month, String> map = new HashMap<Month, String>();
        HashSet<Month> months = new HashSet<Month>();
        for (Account elem : accounts){
            months.add(elem.getBirthday().getMonth());
        }
        for (Month month : months){
            map.put(month, "");
        }
        for (Account elem : accounts){
            List<String> names;
            if (map.get(elem.getBirthday().getMonth()).length() == 0) names = new ArrayList<String>();
            else names = new ArrayList<String>(Arrays.asList(map.get(elem.getBirthday().getMonth()).split(", ")));
            names.add(elem.getFirstname());
            map.put(elem.getBirthday().getMonth(), String.join(", ", names));
        }
        return map;*/
        return accounts.stream().collect(groupingBy(a -> a.getBirthday().getMonth(),
                mapping(Account::getFirstname, joining(", "))));
    }

    /**
     * Returns a {@link Map} where key is a {@link Month} of {@link Account#creationDate}, and value is total balance
     * of all accounts that have the same value creation month.
     *
     * @return a map where key is a creation month and value is total balance of all accounts created in that month
     */
    public Map<Month, BigDecimal> groupTotalBalanceByCreationMonth() {
        /*
        Map<Month, BigDecimal> map = new HashMap<Month, BigDecimal>();
        HashSet<Month> months = new HashSet<Month>();
        for (Account elem : accounts){
            months.add(elem.getCreationDate().getMonth());
        }
        for (Month month : months){
            map.put(month, BigDecimal.valueOf(0));
        }
        for (Account elem : accounts){
            BigDecimal sum = map.get(elem.getCreationDate().getMonth());
            map.put(elem.getCreationDate().getMonth(), sum.add(elem.getBalance()));
        }
        return map;
         */
        return accounts.stream().collect(groupingBy(a -> a.getCreationDate().getMonth(),
                mapping(Account::getBalance, reducing(BigDecimal.ZERO, BigDecimal::add))));
    }

    /**
     * Returns a {@link Map} where key is a letter {@link Character}, and value is a number of its occurrences in
     * {@link Account#firstName}.
     *
     * @return a map where key is a letter and value is its count in all first names
     */
    public Map<Character, Long> getCharacterFrequencyInFirstNames() {
        /*
        Map<Character, Long> map = new HashMap<Character, Long>();
        for (int i=0; i<26; i++){
            map.put((char) (65 + i), (long) 0);
            map.put((char) (97 + i), (long) 0);
        }
        for (Account elem : accounts){
            char[] firstnames = elem.getFirstname().toCharArray();
            for (char firstname : firstnames) {
                long lastVal = map.get(firstname);
                map.put(firstname, lastVal + 1);
            }
        }
        return map;
         */
        return accounts.stream().map(Account::getFirstname).flatMapToInt(String::chars).
                mapToObj(c -> (char) c).collect(groupingBy(identity(), counting()));
    }

    /**
     * Returns a {@link Map} where key is a letter {@link Character}, and value is a number of its occurrences ignoring
     * case, in all {@link Account#firstName} and {@link Account#lastName}. All letters should stored in lower case.
     *
     * @return a map where key is a letter and value is its count ignoring case in all first and last names
     */
    public Map<Character, Long> getCharacterFrequencyIgnoreCaseInFirstAndLastNames() {
        /*
        Map<Character, Long> map = new HashMap<Character, Long>();
        for (int i=0; i<26; i++){
            map.put((char) (97 + i), (long) 0);
        }
        for (Account elem : accounts){
            char[] firstlastnames = (elem.getFirstname() + elem.getLastname()).toLowerCase().toCharArray();
            for (char firstlastname : firstlastnames) {
                long lastVal = map.get(firstlastname);
                map.put(firstlastname, lastVal + 1);
            }
        }
        return map;

         */
        return accounts.stream()
                .flatMap(a -> Stream.of(a.getFirstname(), a.getLastname()))
                .map(String::toLowerCase)
                .flatMapToInt(String::chars)
                .mapToObj(c -> (char) c)
                .collect(groupingBy(identity(), counting()));
    }

}

