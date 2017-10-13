package seedu.address.logic.commands;

import static seedu.address.logic.commands.AddCommand.MESSAGE_DUPLICATE_PERSON;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;


/**
 * Bookmarks a place identified using it's last displayed index from the address book.
 */
public class BookmarkCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "bookmark";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds bookmark to specified place.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_BOOKMARK_SUCCESS = "Bookmarked place: %1$s";

    private static final String MESSAGE_DUPLICATE_TAGS = "Person already has that tag.";

    private final Index targetIndex;
    private final String BOOKMARK_STRING = "Bookmarked";

    public BookmarkCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToBookmark = lastShownList.get(targetIndex.getZeroBased());

        try {
            Tag bookmarkTag = new Tag(BOOKMARK_STRING);
            model.addTag(personToBookmark, bookmarkTag);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            assert false : "The target person cannot be missing";
        } catch (UniqueTagList.DuplicateTagException dte) {
            throw new CommandException(MESSAGE_DUPLICATE_TAGS);
        } catch (IllegalValueException e) {
            assert false : "Tag cannot be invalid";
        }

        return new CommandResult(String.format(MESSAGE_BOOKMARK_SUCCESS, personToBookmark));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BookmarkCommand // instanceof handles nulls
                && this.targetIndex.equals(((BookmarkCommand) other).targetIndex)); // state check
    }
}

