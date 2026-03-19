package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class PersonCardTest extends ApplicationTest {

    @Test
    public void constructor_validPerson_createsCard() {
        Person person = new PersonBuilder().withName("Alice").withTags("course:CS2103").build();
        PersonCard personCard = new PersonCard(person, 1);

        assertNotNull(personCard);
        assertEquals(person, personCard.person);
    }

    @Test
    public void tags_sortedByUiPriority() {
        Person person = new PersonBuilder().withName("Bob")
                .withTags("course:CS2103", "tut:D24", "lab:D24", "friends")
                .build();
        PersonCard personCard = new PersonCard(person, 1);

        FlowPane tagsPane = personCard.getTagsPane();
        // Assuming tags are sorted by uiPriority: course (0), tut (1), lab (2), friends (999)
        assertEquals(4, tagsPane.getChildren().size());
        Label firstLabel = (Label) tagsPane.getChildren().get(0);
        assertEquals("course:CS2103", firstLabel.getText());
        assertTrue(firstLabel.getStyleClass().contains("course-tag"));

        Label secondLabel = (Label) tagsPane.getChildren().get(1);
        assertEquals("tut:D24", secondLabel.getText());
        assertTrue(secondLabel.getStyleClass().contains("tutorial-tag"));

        Label thirdLabel = (Label) tagsPane.getChildren().get(2);
        assertEquals("lab:D24", thirdLabel.getText());
        assertTrue(thirdLabel.getStyleClass().contains("lab-tag"));

        Label fourthLabel = (Label) tagsPane.getChildren().get(3);
        assertEquals("friends", fourthLabel.getText());
        assertTrue(fourthLabel.getStyleClass().contains("default-tag"));
    }

    @Test
    public void tags_singleTag_displaysCorrectly() {
        Person person = new PersonBuilder().withName("Charlie").withTags("course:CS2103").build();
        PersonCard personCard = new PersonCard(person, 1);

        FlowPane tagsPane = personCard.getTagsPane();
        assertEquals(1, tagsPane.getChildren().size());
        Label label = (Label) tagsPane.getChildren().get(0);
        assertEquals("course:CS2103", label.getText());
        assertTrue(label.getStyleClass().contains("course-tag"));
    }

    @Test
    public void tags_noTags_emptyPane() {
        Person person = new PersonBuilder().withName("David").build();
        PersonCard personCard = new PersonCard(person, 1);

        FlowPane tagsPane = personCard.getTagsPane();
        assertEquals(0, tagsPane.getChildren().size());
    }
}
