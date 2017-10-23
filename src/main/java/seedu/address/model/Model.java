package seedu.address.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<ReadOnlyPerson> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyAddressBook newData);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /** Deletes the given person. */
    void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException;

    /** Adds the given person */
    void addPerson(ReadOnlyPerson person) throws DuplicatePersonException;


    /**
     * Adds tag to target person in address book.
     *
     * @throws PersonNotFoundException if person cannot be found in the list.
     *
     * @throws DuplicatePersonException if after removal, the person's details causes the person to be
     *      equivalent to another existing person in the list.
     *
     * @throws UniqueTagList.DuplicateTagException if added tag is a duplicate.
     */
    void addTag(ReadOnlyPerson person, Tag tag) throws DuplicatePersonException, PersonNotFoundException,
                                                    UniqueTagList.DuplicateTagException;

    /**
     * Removes given tag from all persons in address book.
     *
     * @throws PersonNotFoundException if person cannot be found in the list.
     *
     * @throws DuplicatePersonException if after removal, the person's details causes the person to be
     *      equivalent to another existing person in the list.
     */
    void removeAllTags(Tag tagName) throws PersonNotFoundException, DuplicatePersonException;

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     *
     * @throws DuplicatePersonException if updating the person's details causes the person to be equivalent to
     *      another existing person in the list.
     * @throws PersonNotFoundException if {@code target} could not be found in the list.
     */
    void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
            throws DuplicatePersonException, PersonNotFoundException;

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<ReadOnlyPerson> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate);

}
