package moss.algorithm;

import java.io.IOException;
import java.io.Reader;

/**
 * Interface for all comparison strategies for the MOSS project
 * All subclasses of this class are obligated to:
 * 1. Restore their internal state to its original state after comparison. It is recommended that there be no way to store internal state if possible.
      and instead stick to the use of local variables
 */
@FunctionalInterface
public interface ComparisonStrategy {
    Double compare(Reader str1, Reader str2) throws IOException;
}
<?xml version="1.0" encoding="UTF-8"?>

<?import javafx.scene.layout.ColumnConstraints?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.RowConstraints?>

<?import javafx.scene.control.ScrollPane?>
<!--CHANGE: Changed anchor pane to scroll pane-->
<ScrollPane prefHeight="400.0" prefWidth="600.0" xmlns="http://javafx.com/javafx/8.0.171" xmlns:fx="http://javafx.com/fxml/1" fx:controller="moss.gui.CorrelationMatrixMenu.CorrelationMatrixMenuPresenter">
      <GridPane fx:id="correlationMatrixTable" gridLinesVisible="true" layoutX="47.0" layoutY="32.0">
        <columnConstraints>
          <ColumnConstraints hgrow="NEVER" minWidth="100.0" prefWidth="100.0" />
        </columnConstraints>
        <rowConstraints>
          <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
        </rowConstraints>
      </GridPane>
</ScrollPane>
package moss.gui.CorrelationMatrixMenu;

import moss.projectpairmachine.ProjectsCorrelationMatrix;


/**
 * Data passed unto the correlation matrix menu
 */
public class CorrelationMatrixMenuModel {
    private ProjectsCorrelationMatrix correlationMatrix;

    /**
     * @param matrix Matrix to be loaded into the correlation matrix menu
     */
    public void loadMatrix(ProjectsCorrelationMatrix matrix){
        this.correlationMatrix = matrix;
    }

    /**
     * @return Gets the matrix to be loaded into the menu
     */
    final ProjectsCorrelationMatrix getMatrix(){
        //NOTE: This is package-private because the matrix will only be used for display and should not be tampered with from other locations
        return correlationMatrix;
    }


}
package moss.gui.CorrelationMatrixMenu;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TableView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import moss.gui.utilities.CustomColorOperations;
import moss.gui.utilities.CustomFXMLOperations;
import moss.projectpairmachine.ProjectsCorrelationMatrix;
import moss.projectpairmachine.ProjectsCorrelationMatrix.*;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Presenter for the correlation matrix
 */
public class CorrelationMatrixMenuPresenter {
    @FXML
    private GridPane correlationMatrixTable;
    @Inject
    private CorrelationMatrixMenuModel model;
    @Inject
    private CorrelationMatrixMenuService services;


    /**
     * Loads the matrix in the correlation menu model into the window via a grid pane
     */
    private void loadModelMatrixToTable(){
        final ProjectsCorrelationMatrix matrix = model.getMatrix();
        //PHASE 1: Load headers
        Collection<Text> nameText = new ArrayList<>();
        for (String name : matrix.getProjectNames()){
            nameText.add(new Text(name));
        }
        Text[] nameTextArray = new Text[nameText.size()];
        nameText.toArray(nameTextArray);
        correlationMatrixTable.addRow(0, nameTextArray);


        //PHASE 2: Load all the results from the table into the grid pane
        int row = 1;
        for (ResultRow resultRow : matrix.getRows()){
            Collection<Pane> resultsPaneInRow = new ArrayList<>();
            resultsPaneInRow.add(new StackPane(new Text(resultRow.getProject().getName())));
            for (ResultSet.ResultRecord result : resultRow.getResults()){
                Pane scoreTextContainer = new StackPane(new Text(String.format("%.2f", result.getScore())));
                scoreTextContainer.setBackground(
                        new Background(
                                new BackgroundFill(CustomColorOperations
                                        .interpolateColor(Color.GREEN, Color.RED, result.getScore()), null, null)
                        )
                );
                resultsPaneInRow.add(scoreTextContainer);
            }

            Pane[] resultTextArray = new Pane[resultsPaneInRow.size()];
            resultsPaneInRow.toArray(resultTextArray);
            correlationMatrixTable.addRow(row++, resultsPaneInRow.toArray(resultTextArray));
        }

        correlationMatrixTable.setGridLinesVisible(true);
    }
    @FXML
    private void initialize(){
        loadModelMatrixToTable();
    }

}
package moss.gui.CorrelationMatrixMenu;

/**
 * Represents services that can be done while viewing the correlation matrix
 */
public class CorrelationMatrixMenuService {

}
package moss.gui.CorrelationMatrixMenu;

import com.airhacks.afterburner.views.FXMLView;

/**
 * View for the correlation matrix
 */
public class CorrelationMatrixMenuView extends FXMLView {

}
package moss.gui.utilities;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 * A custom set of operations for working with colors in Java
 */
public final class CustomColorOperations {
    private CustomColorOperations(){}

    /**
     * Used to find a color at a particular percentage between a start and end color.
     * For example, you may use this to find a color 60% between red an dblue
     * @param start Starting color
     * @param end Ending color
     * @param percentage Percentage between the two colors of the output color
     * @return Color at the <b>percentage</b> * 100% of start and end
     */
    public static Paint interpolateColor(Color start, Color end, double percentage){
        return start.interpolate(end, percentage);
    }

}
package moss.gui.utilities;

import com.airhacks.afterburner.views.FXMLView;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Contains utility operation methods for FXML view communication and processing
 */
public final class CustomFXMLOperations {
    //This class cannot be constructed from the outside because it is only meant as a container for basic GUI operations
    private CustomFXMLOperations(){ /*NOTE: this class is not instantiable*/ }

    /**
     * @param viewClass Class instance of the FXML view to be loaded unto the screen
     */
    public static void showFxmlViewInWindow(Class<? extends FXMLView> viewClass){
        try {
            FXMLView view = viewClass.newInstance();
            Stage newStage = new Stage();
            newStage.setScene(new Scene(view.getView()));
            newStage.show();

        } catch (InstantiationException | IllegalAccessException e) {
            //these exceptions have been blocked because they give no additional information to the outside user.
            //furthermore, the afterburner library, where the FXMLView class was taken from, is known to be reliable
            e.printStackTrace();
        }
    }
}
package moss.algorithm;

import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Creates tokens and token clusters from I/O readers
 */
@SuppressWarnings("unused")
//CHANGE: Suppressed unused warning
class HashingTokenizer {
    private StreamTokenizer tokenizer;
    //CHANGE: this was moved from the TokenClusterOccurrenceTable class because it is more efficient to just ignore a token right from the start
    /*CHANGE: Removed black-listing. It cannot be found anywhere in the entire project. There are currently too many issues with implementing a blacklist
     *such as where to filter it out. However, it is important that it is implemented at some point in the future because there is actually a need to
     * ignore certain common tokens such as if and for because they occur too much in the code to be reliable
     * Thus,
     * TODO: Implement black-listing (see comment in HashingTokenizer for details)
    * */
    static final int QUOTE = '"';

    static class TokenizerEndException extends Exception{
       TokenizerEndException(String message){
           super(message);
       }
    }

    public void addCharacterToken(int ch){
        tokenizer.ordinaryChar(ch);
    }

    /**isAtEnd simply checks if the next if the next token is the end-of-file character
     * In general, this tokenizer will not return an exception once you try to read beyond
     * @return Whether the end-of-file token has been reached
     * */
    final boolean isAtEnd(){
        //A significant design decision here is the fact that an IOException will crash the system unwarranted after printing the stack trace
        //this function will not tamper with the order of the tokens
        tokenizer.pushBack();
        try {
            return tokenizer.nextToken() == StreamTokenizer.TT_EOF;
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return true;
    }


    /**
     * @return the next token yet to be read from the Reader.
     * @throws IOException throws when the next token cannot be read from the Reader
     */
    final Token getNextTokenInfo() throws IOException, TokenizerEndException {
        //CHANGE: The EOF token has been removed from the tokenizer. The user of the class might need to do an isAtEnd check before using this
        if (this.isAtEnd()) throw new TokenizerEndException("Reached end of file. Use isAtEnd method to do prior checking");
        Token.TokenBuilder tokenBuilder = new Token.TokenBuilder();
        int tokenNumber = tokenizer.nextToken();
        int id;
        switch(tokenNumber){
            case StreamTokenizer.TT_EOL:
                tokenBuilder.setType(Token.TYPE.EOL);
                break;
            case StreamTokenizer.TT_NUMBER:
                //CHANGE: recently changed id for number to the NUMBER constant instead of the integer's hash value
                tokenBuilder.setTypeWithValue(Token.TYPE.NUMBER, tokenizer.nval);
                break;
            case StreamTokenizer.TT_WORD:
                //For strings
                tokenBuilder.setTypeWithValue(Token.TYPE.WORD, tokenizer.sval);
                break;
            default:
                //extra checks
                //a quote check here
                if (tokenNumber == QUOTE) tokenBuilder.setTypeWithValue(Token.TYPE.STRING_LITERAL, tokenizer.sval);
                else tokenBuilder.setTypeWithValue(Token.TYPE.OTHER, tokenNumber);
                break;
        }
        return tokenBuilder.setLineNo(tokenizer.lineno()).createToken();
    }

    /**
     * this creates a sequence (cluster) of an arbitrary number of tokens yet to be read
     * @see TokenCluster
     * @param size the number of tokens to be clustered
     * @return returns a cluster of <b>at least</b><i>size</i> tokens. If the end-of-file has been reached, it will return however many remained
     * @throws TokenizerEndException thrown only when there are no more tokens remaining to begin with (see return value)
     *
     */
    final TokenCluster getNextTokenCluster(int size) throws IOException, TokenizerEndException {
        //Notes on the TokenizerEndException: this cannot be contained here because it will just return an empty cluster, which the user might not expect.
        /*Phase 1: this is the initial isAtEnd check, in the scenario that there is no more tokens right at the start of the call,
          a TokenizerEndException is thrown immediately. */
        if (this.isAtEnd()) throw new TokenizerEndException(
                "Cluster could not be formed. No tokens could be read prior to the end of file. " +
                "Consider using isAtEnd method for a priori checking");


        /*Phase 2: clusters the next at-least [size] tokens.*/
        TokenCluster.TokenClusterBuilder clusterOrganizer = new TokenCluster.TokenClusterBuilder();
        for (int i = 0; i < size; ++i){
            // since the first end-check has been passed by this point,
            // any encounter with the end-of-file just stops further collection (hence the break)
            if (this.isAtEnd()) break;
            clusterOrganizer.addNext(this.getNextTokenInfo());
        }

        return clusterOrganizer.cluster();
    }

    /**
     * @param size size of the token clusters to be returned
     * @return a collection of all clusters of a particular <i>size</i> from the Reader.
     */
    final Collection<TokenCluster> remainingTokenClusters(int size) throws IOException {
        Collection<TokenCluster> tokenClusters = new ArrayList<>();
        while(!this.isAtEnd()) {
            try {
                tokenClusters.add(this.getNextTokenCluster(size));
            } catch (TokenizerEndException ignored){}
            //IMPORTANT NOTE: this exception is contained in this class because the isAtEnd at the top will help us avoid this anyway
        }
        return tokenClusters;
    }



    HashingTokenizer(Reader reader){
        tokenizer = new StreamTokenizer(reader);
        tokenizer.quoteChar(QUOTE);


    }
}
import com.airhacks.afterburner.views.FXMLView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import moss.algorithm.ComparisonStrategy;
import moss.algorithm.TokenHashingStrategy;
import moss.gui.MultiProjectMenu.MultiProjectMenuView;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main extends Application {
    public static void main(String[] args) throws IOException {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        MultiProjectMenuView startingPoint = new MultiProjectMenuView();
        Scene startingScene = new Scene(startingPoint.getView());
        primaryStage.setScene(startingScene);
        primaryStage.show();

    }
}
package moss.projectpairmachine;

import moss.project.MultiProjectStorage;

public interface MultiProjectComparison {
    //CHANGE: Changed to public interface to allow other packages to hide the particular comparison system they are using.
    ProjectsCorrelationMatrix compareAll(MultiProjectStorage projects);

}
<?xml version="1.0" encoding="UTF-8"?>

<?import javafx.scene.control.Button?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.control.ListView?>
<?import javafx.scene.control.RadioButton?>
<?import javafx.scene.control.Tab?>
<?import javafx.scene.control.TabPane?>
<?import javafx.scene.control.TextField?>
<?import javafx.scene.control.ToggleGroup?>
<?import javafx.scene.layout.AnchorPane?>

<AnchorPane prefHeight="400.0" prefWidth="814.0" xmlns="http://javafx.com/javafx/8.0.171" xmlns:fx="http://javafx.com/fxml/1" fx:controller="moss.gui.MultiProjectMenu.MultiProjectMenuPresenter">
   <children>
      <TabPane layoutX="33.0" layoutY="33.0" prefHeight="305.0" prefWidth="544.0" tabClosingPolicy="UNAVAILABLE">
        <tabs>
          <Tab text="Multi-Project Folder">
            <content>
              <AnchorPane minHeight="0.0" minWidth="0.0" prefHeight="245.0" prefWidth="350.0">
                     <children>
                        <Button fx:id="projectPathChooseButton" layoutX="56.0" layoutY="41.0" mnemonicParsing="false" prefHeight="31.0" prefWidth="71.0" text="Choose" />
                        <Label fx:id="projectPathLabel" layoutX="137.0" layoutY="46.0" prefHeight="21.0" prefWidth="332.0" text="None" />
                        <Button fx:id="startComparisonButton" layoutX="56.0" layoutY="158.0" mnemonicParsing="false" prefHeight="31.0" prefWidth="269.0" text="Compare" />
                        <Label fx:id="projectCountLabel" layoutX="133.0" layoutY="72.0" text="0" />
                        <Label layoutX="153.0" layoutY="72.0" text="Projects Found" />
                     </children></AnchorPane>
            </content>
          </Tab>
          <Tab text="Separate Projects">
            <content>
              <AnchorPane minHeight="0.0" minWidth="0.0" prefHeight="180.0" prefWidth="200.0">
                     <children>
                        <ListView fx:id="includedProjectsListView" layoutX="45.0" layoutY="68.0" prefHeight="155.0" prefWidth="296.0" />
                        <Button fx:id="addProjectButton" layoutX="45.0" layoutY="26.0" mnemonicParsing="false" text="Add Project..." />
                        <Button fx:id="compareSeparateButton" layoutX="371.0" layoutY="183.0" mnemonicParsing="false" text="Compare" />
                     </children></AnchorPane>
            </content>
          </Tab>
        </tabs>
      </TabPane>
      <ListView fx:id="filterListView" layoutX="589.0" layoutY="200.0" prefHeight="170.0" prefWidth="200.0" />
      <RadioButton fx:id="regexRadio" layoutX="689.0" layoutY="102.0" mnemonicParsing="false" text="REGEX">
         <toggleGroup>
            <ToggleGroup fx:id="filterType" />
         </toggleGroup>
      </RadioButton>
      <RadioButton fx:id="glubRadio" layoutX="689.0" layoutY="132.0" mnemonicParsing="false" text="GLUB" toggleGroup="$filterType" />
      <TextField fx:id="filterText" layoutX="609.0" layoutY="69.0" promptText="Filter" />
   </children>
</AnchorPane>
package moss.gui.MultiProjectMenu;

import moss.project.PathFilter;

import javax.inject.Inject;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Model for the multi-project menu. Contains stuff to be displayed by the table menu
 */
@SuppressWarnings("WeakerAccess")
public final class MultiProjectMenuModel{
    //CHANGE: Changed to final to prevent inheritance
    private Path chosenProjectsPath;
    @Inject
    private PathFilter.PathFilterBuilder filter;

    /**
     * @return Chosen project path
     */
    public Path getChosenProjectsPath() {
        return chosenProjectsPath;
    }

    /**
     * @param chosenProjectsPath Chosen project path
     */
    public void setChosenProjectsPath(Path chosenProjectsPath) {
        this.chosenProjectsPath = chosenProjectsPath;
    }


    /**
     * @return Filter used by multi-project menu
     */
    PathFilter.PathFilterBuilder getFilterBuilder() {
        return filter;
    }

    /**
     * @return The filter built from the filter builder.
     */
    public PathFilter createFilter(){
        return filter.createFilter();
    }
}
package moss.gui.MultiProjectMenu;


import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import moss.gui.CorrelationMatrixMenu.CorrelationMatrixMenuModel;
import moss.gui.CorrelationMatrixMenu.CorrelationMatrixMenuView;
import moss.gui.utilities.CustomFXMLOperations;
import moss.project.PathFilter;

import javax.inject.Inject;
import java.nio.file.Path;

/**
 * Controls the multi-project menu
 */
public class MultiProjectMenuPresenter {
    //MENU ITEMS: SEPARATE ITEM MENU
    @FXML
    private ListView<Path> includedProjectsListView;
    @FXML
    private Button addProjectButton;
    @FXML
    private Button compareSeparateButton;

    //FILTER ITEMS
    @FXML
    private RadioButton regexRadio;
    @FXML
    private RadioButton glubRadio;
    @FXML
    private TextField filterText;
    @FXML
    private ToggleGroup filterType;
    @FXML
    private ListView<String> filterListView;

    //MULTI-PROJECT FOLDER COMPARISON
    @FXML
    private Button startComparisonButton;
    @FXML
    private Label projectCountLabel;
    @FXML
    private Label projectPathLabel;
    @FXML
    private Button projectPathChooseButton;



    //RENAME: renamed from Controller to Presenter to avoid confusion while reading papers on FXML conventions
    @Inject
    private MultiProjectMenuService services;
    @Inject
    private MultiProjectMenuModel singlePathProjectsModel;
    @Inject
    private SeparateProjectMenuModel separateProjectMenuModel;
    @Inject
    private CorrelationMatrixMenuModel correlationMatrixModel;


    @FXML
    private void initialize(){
        //PART 1: Project path selection
        projectPathChooseButton.setOnMouseClicked((event -> {
            //PROJECT PATH CHOOSE BUTTON ACTION
            Path requestedPath = services.requestDirectoryFromUser();
            singlePathProjectsModel.setChosenProjectsPath(requestedPath);
            projectPathLabel.setText(String.valueOf(requestedPath));
            projectCountLabel.setText(String.valueOf(services.folderCount(requestedPath)));
            startComparisonButton.setDisable(false);
        }));

        //PART 2: Text filter
        filterText.setOnAction((event -> {
            filterListView.getItems().add(filterText.getText());
            singlePathProjectsModel.getFilterBuilder().addFilter(filterText.getText());
        }));
        //VERBOSE: This simply takes the underlying value of the newly selected radio button (Filter type) and
        //injects that to the filter builder
        filterType.selectedToggleProperty().addListener((observable, oldValue, newValue) ->
                singlePathProjectsModel.getFilterBuilder().setFilterType((PathFilter.Type) newValue.getUserData()));
        regexRadio.setUserData(PathFilter.Type.REGEX);
        glubRadio.setUserData(PathFilter.Type.GLOB);


        //PART 3: Comparison button
        startComparisonButton.setDisable(true);
        startComparisonButton.setOnMouseClicked((event -> {
            //creates the correlation matrix and loads it into the correlation matrix menu's singlePathProjectsModel
            correlationMatrixModel.loadMatrix(services.processMultiProjectFolder(singlePathProjectsModel.getChosenProjectsPath(), singlePathProjectsModel.createFilter()));

            //Load correlation matrix menu
            CustomFXMLOperations.showFxmlViewInWindow(CorrelationMatrixMenuView.class);
        }));




        //PART 4: ADD PROJECT BUTTON
        addProjectButton.setOnMouseClicked(event ->{
            Path pathToBeAdded = services.requestDirectoryFromUser();
            separateProjectMenuModel.addProject(pathToBeAdded);
            includedProjectsListView.getItems().add(pathToBeAdded);
        });

        //PART 5: Compare separate projects button
        compareSeparateButton.setOnMouseClicked(event -> {
            correlationMatrixModel.loadMatrix(services.processMultiPaths(
                            separateProjectMenuModel.retrieveProjectPaths(), singlePathProjectsModel.createFilter()));
            CustomFXMLOperations.showFxmlViewInWindow(CorrelationMatrixMenuView.class);
                }
        );


    }
}
package moss.gui.MultiProjectMenu;

import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import moss.algorithm.TokenHashingStrategy;
import moss.project.MultiProjectStorage;
import moss.project.PathFilter;
import moss.projectpairmachine.ProjectsCorrelationMatrix;
import moss.projectpairmachine.SimpleMultiProjectComparison;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.stream.Stream;

/**
 * Services to be used by the multi-project menu GUI
 */
@SuppressWarnings("WeakerAccess")
public class MultiProjectMenuService {
    //CHANGE: Removed @Inject for comparisonMachine because it makes no difference. Only one instance of this class will be used.
    //A static reference will be used instead
    private static final SimpleMultiProjectComparison comparisonMachine = SimpleMultiProjectComparison.fromStrategy(new TokenHashingStrategy());

    /**
     * @param projectsFolder Folder containing all the projects to be compared
     * @param filters Filters to be imposed upon the project folders
     * @return A matrix containing the results
     */
    public ProjectsCorrelationMatrix processMultiProjectFolder(Path projectsFolder, PathFilter filters){
        MultiProjectStorage projects =
                MultiProjectStorage.projectsIn(projectsFolder, filters);
        return comparisonMachine.compareAll(projects);
    }


    /**
     * @param folder Path whose subdirectories will be counted
     * @return Number of folders/directories in the <b>folder</b> path
     */
    public int folderCount(Path folder){
        int count = 0;
        try(Stream<Path> paths = Files.list(folder)) {
            //Removes all non-directories then returns the count of whatever remains
            return (int) paths
                        .filter(Files::isDirectory)
                        .count();
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }



    /**
     * @param paths Collection of paths to be included in the project
     * @param filters Filters to be imposed upon the projects
     * @return A matrix containing the results
     */
    public ProjectsCorrelationMatrix processMultiPaths(Collection<Path> paths, PathFilter filters){
        MultiProjectStorage projects =
                MultiProjectStorage.fromPathCollection(paths, filters);
        return comparisonMachine.compareAll(projects);
    }


    /**
     * This will bring up
     * @return Path given by the user
     */
    Path requestDirectoryFromUser() {
        //TODO: Consider moving this to the multi-project menu presenter. Having a GUI-based request feels out of place here
        //NOTE: This has been made package-private precisely because of the reasons stated above.
        final DirectoryChooser directoryChooser = new DirectoryChooser();
        final File requestedDirectory = directoryChooser.showDialog(new Stage());
        //We need to check if the directory is valid
        if (requestedDirectory != null){
            return requestedDirectory.toPath();
        } else{
            //if the user fails to pick a path, the original path he came from will be returned instead
            //this is to prevent errors from happening in the system.
            return directoryChooser.getInitialDirectory().toPath();
        }

    }
}
package moss.gui.MultiProjectMenu;

import org.junit.Test;

import javax.inject.Inject;
import moss.project.TestObjects;

import static org.junit.Assert.*;

/**
 * Used to test the multi-project menu services
 */
public class MultiProjectMenuServiceTest {
    @Inject
    MultiProjectMenuService testService = new MultiProjectMenuService();

    /**
     * Format: int folderCount(Path folder)
     */
    @Test
    public void folderCount() {
        assert(testService.folderCount(TestObjects.SUBMISSIONS_PATH) == TestObjects.SUBMISSION_PATH_FOLDER_COUNT);
    }
}
package moss.gui.MultiProjectMenu;

import com.airhacks.afterburner.views.FXMLView;

/**
 * View for the multi-project menu
 */
public class MultiProjectMenuView extends FXMLView {
}
package moss.project;

import com.sun.org.apache.xpath.internal.operations.Mult;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Stream;

/**
 * Class that stores multiple projects
 */
public class MultiProjectStorage implements Iterable<Project>{
    //NOTES on this iterator: The decision to make this project iterator is motivated mainly by the fact that
    //I have observed that another module has become too dependent on multiple objects of this package.

    final private Collection<Project> projects;

    /**
     * @return Number of projects stored
     */
    public int projectCount(){
        return projects.size();
    }

    /**
     * @return An iterator for all the contained projects
     */
    @Override
    public final Iterator<Project> iterator() {
        return projects.iterator();
    }

    /**
     * Takes projects from the subdirectories of a path and stores them
     * @param projectsFolder Folder that contains the projects
     * @param filter Path filter for files
     * @return An iterable storage
     */
    public static MultiProjectStorage projectsIn(Path projectsFolder, PathFilter filter){
        return new MultiProjectStorage(projectsFolder, filter);
    }

    /**
     * Takes projects from a collection and stores them
     * @param projects Collection of projects to be stored
     * @return An iterable storage of projects
     */
    public static MultiProjectStorage fromCollection(Collection<Project> projects){
        return new MultiProjectStorage(projects);
    }

    /**
     * @param projectPaths Collection of paths to each project to be added
     * @return Storage for project objects for every path in the collection
     */
    public static MultiProjectStorage fromPathCollection(Collection<Path> projectPaths, PathFilter filters){
        return new MultiProjectStorage(projectPaths, filters);
    }


    /**
     * @param projectPaths Collection of paths to each project to be added
     */
    private MultiProjectStorage(Collection<Path> projectPaths, PathFilter filters){
        this.projects = new ArrayList<>();
        for (Path projectPath : projectPaths){
            //TODO: This is precisely the same snippet of code as the one from the other constructor. Consider code extraction
            ProjectBuilder projectBuilder = new ProjectBuilder();
            projects.add(projectBuilder
                    .setPath(projectPath)
                    .setFilter(filters)
                    .createProject());
        }
    }

    /**
     * @param projects Collection of projects to be stored
     */
    private MultiProjectStorage(Collection<Project> projects){
        this.projects = projects;
    }


    /**
     * @param projectsFolder Folder containing projects to be stored
     * @param filter Filter for particular types of files
     */
    private MultiProjectStorage(Path projectsFolder, PathFilter filter){
        projects = new ArrayList<>();

        try (Stream<Path> projectPath = Files.list(projectsFolder)) {
            projectPath
                    .filter(Files::isDirectory)
                    .forEach((path) -> {
                        ProjectBuilder projectBuilder = new ProjectBuilder();
                        projects.add(projectBuilder
                                .setPath(path)
                                .setFilter(filter)
                                .createProject());
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
package moss.project;

import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.Collection;
import java.util.Collections;

/*the purpose of this class is to collect
* a bunch of readers, combine them into a single
* buffer of memory, and generate a byte stream
* when necessary.
* The benefits of this class is that it minimizes the actual number of copies of the characters needed by the package.
* */
class MultiStreamReaderGenerator {
    private final byte[] combinedByteArray;

    /**
     * @param streams Input streams to be combined
     * @throws IOException Thrown when one input stream fails
     */
    MultiStreamReaderGenerator(Collection<? extends InputStream> streams) throws IOException {
        SequenceInputStream combinedReaderStream =
                new SequenceInputStream(Collections.enumeration(streams));
        ByteArrayOutputStream byteos = new ByteArrayOutputStream();
        IOUtils.copy(combinedReaderStream, byteos);
        combinedByteArray = byteos.toByteArray();
    }

    /**
     *
     *creates an independent reader to the collection of streams. This means that the generated readers are independent of one another.
     * but at the same time, it does not distribute any unnecessary copies of the combined streams
     *
     * @return Reader generated from combining all the input streams
     * */
    final Reader generate(){
        return new BufferedReader(
                new InputStreamReader(
                        new ByteArrayInputStream(combinedByteArray)));
    }
}
package moss.project;

import org.junit.Test;
import org.junit.Before; 
import org.junit.After;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;


public class MultiStreamReaderGeneratorTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
    *
    * Method: generate()
    * @throws Exception All thrown exceptions
    */
    @Test
    public void testGenerate() throws Exception {
    //TODO: Test goes here...
            ByteArrayInputStream byteStream1 = new ByteArrayInputStream("A".getBytes());
            ByteArrayInputStream byteStream2 = new ByteArrayInputStream("B".getBytes());
            Collection<InputStream> streams = new ArrayList<>();
            streams.add(byteStream1);
            streams.add(byteStream2);
            MultiStreamReaderGenerator generator = new MultiStreamReaderGenerator(streams);
            Reader reader = generator.generate();
            assert(reader.read() == 'A');
            assert(reader.read() == 'B');

        }

}


package moss.project;

import java.awt.*;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * Used for filtering paths
 */
@SuppressWarnings("WeakerAccess")
public final class PathFilter {
    //CHANGE: Made class immutable and created builder to mediate immutability


    static public final PathFilter CPP_FILTER = new PathFilter(Type.GLOB, "**/*.cpp");
    static public final PathFilter JAVA_FILTER = new PathFilter(Type.GLOB, "**/*.java");
    static public final PathFilter CPP_AND_JAVA_FILTER = new PathFilter(Type.GLOB,"**/*.{java, cpp, h}");
    static public final PathFilter TXT_FILTER = new PathFilter(Type.GLOB,"**/*.txt");
    static public final PathFilter NO_FILTER = new PathFilter(Type.GLOB,"**/*");

    public enum Type{
        //CHANGE: Made Type enum public
        REGEX("regex"), GLOB("glob");

        String filterNameID;

        /**
         * Sole constructor.  Programmers cannot invoke this constructor.
         * It is for use by code emitted by the compiler in response to
         * enum type declarations.
         *
         * @param name    - The name of this enum constant, which is the identifier
         *                used to declare it.
         */
        Type(String name) {
            this.filterNameID = name;
        }

        /**
         * @return Filter ID string
         */
        String getFilterTypeIdentifier(){return filterNameID;}
    }

    private final Type filterType;
    private final Collection<String> filters;

    /**
     * @param path Path to be matched
     * @return If the path matches any of the filters
     */
    final boolean matchesAll(Path path){
        //Made package private because this is not to be used anywhere else but this module
        boolean matches = false;
        for (String filter : filters) {
            matches |= FileSystems.getDefault().getPathMatcher(filterType.getFilterTypeIdentifier() + ":" + filter).matches(path);
        }
        return matches;
    }

    /**
     * Builder for path filters
     */
    public static class PathFilterBuilder {
        private Collection<String> filters = new ArrayList<>();
        private Type filterType = Type.REGEX;
        /**
         * @param filters Filters to be added
         */
        public PathFilterBuilder addAllFilters(Collection<String> filters) {
            this.filters.addAll(filters);
            return this;
        }

        /**
         * @param filter Filter text to be added
         */
        public PathFilterBuilder addFilter(String filter) {
            filters.add(filter);
            return this;
        }

        /**
         * @param filter Filter to be removed
         */
        public PathFilterBuilder removeFilter(String filter) {
            filters.remove(filter);
            return this;
        }

        /**
         * @param filters All filters to be removed
         */
        public PathFilterBuilder removeAllFilters(Collection<String> filters) {
            this.filters.removeAll(filters);
            return this;
        }

        /**
         * @param filterType Filter type of path filter
         */
        public PathFilterBuilder setFilterType(Type filterType) {
            this.filterType = filterType;
            return this;
        }

        /**
         * @return The filter created with all the added filter strings or a NO_FILTER filter if none are added in
         */
        public PathFilter createFilter(){
            //CHANGE: Added a check for emptiness of filter array. This is to ensure that even with a lack of filters, the behavior we will get
            //is exactly that: having no filters.
            if (filters.size() == 0) return PathFilter.NO_FILTER;
            return new PathFilter(filterType, filters);
        }
    }


    private PathFilter(Type filterType, String... initialFilters){
        this.filterType = filterType;
        this.filters = Arrays.asList(initialFilters);
    }

    private PathFilter(Type filterType, Collection<String> initialFilters){
        this.filterType = filterType;
        this.filters = new ArrayList<>(initialFilters);
    }
}
package moss.project;

import java.io.Reader;
import java.nio.file.Path;

/**
 * Holds a programming project folder
 */
public class Project {
    private final String name;
    private final Path path;
    private final ProjectFlatReaderDistributor readerDistributor;


    /**
     * @param path Path to the project folder
     * @param name Name of the project folder
     * @param globFilter The GLOB-formatted filter text used to pick which files can be accessed from this object
     */
    Project(Path path, String name, PathFilter globFilter){
        this.name = name;
        this.path = path;
        readerDistributor = new ProjectFlatReaderDistributor(this.path, globFilter);
    }

    /**
     * @return A <i>Reader</i> that spits out a concatenation of all files in the project.
     * @see Reader
     */
    //CHANGE: This was changed to public because it is needed by some facilities outside the project package
    public final Reader getConcatenatedReader(){
        return readerDistributor.distribute();
    }

    /**
     * @return The path to the project
     * @see Path
     */
    public final Path getPath(){
        return this.path;
    }


    /**
     * @return The name of the project
     */
    public final String getName() {
        return this.name;
    }
}
package moss.project;

import java.nio.file.Path;

/**
 * Builds <i>Project</i> objects.
 */
@SuppressWarnings("ALL")
public class ProjectBuilder {
    private Path path;
    private String name;
    private PathFilter filter;

    /**
     * Creates a builder for a <i>Project</i>
     * No filter by default
     */
    public ProjectBuilder(){
        //CHANGE: Recently made all methods here public to connect this to other modules
        this.filter = PathFilter.NO_FILTER;
        this.name = null;
    }

    /**
     * @param path Path to the project folder
     * @return A builder for the project to be built
     */
    public ProjectBuilder setPath(Path path) {
        this.path = path;
        return this;
    }

    /**
     * @param name Name of the project
     * @return A builder for the project to be built
     */
    public ProjectBuilder setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * @param filter Text filter for the files that can be accessed from the project in GLOB format.
     * @return A builder for the project to be built
     */
    public ProjectBuilder setFilter(PathFilter filter) {
        this.filter = filter;
        return this;
    }

    /**
     * @return The project generated with all values set.
     */
    public Project createProject() {
        if (name == null) this.name = this.path.getFileName().toString();
        return new Project(path, name, filter);
    }
}
package moss.project;


import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Stream;

/**
 * Combines filtered files from a single path to a single reader.
 */
public class ProjectFlatReaderDistributor {
    private final MultiStreamReaderGenerator generator;

    public ProjectFlatReaderDistributor(Path projectPath){
        this(projectPath, PathFilter.NO_FILTER);
    }

    /**
     * if an empty string is passed to extension, this will not do any extension filtering
     * the filter uses the GLOB syntax
     * @param projectPath Path to the project
     * @param globFilter GLOB filter for files to be combined
     */
    public ProjectFlatReaderDistributor(Path projectPath, PathFilter globFilter) {
        MultiStreamReaderGenerator generatorTmp;
        Collection<InputStream> inputFileStreams = new ArrayList<>();

        try(Stream<Path> paths = Files.walk(projectPath)) {
            paths
                    .filter(Files::isRegularFile)
                    .filter(globFilter::matchesAll)
                    .forEach(path -> {
                        try {
                            inputFileStreams.add(Files.newInputStream(path));
                        } catch (IOException e) {
                            //TODO: Handle properly (This will happen if the file stream could not be created)
                            e.printStackTrace();
                        }
                    });
            generatorTmp = new MultiStreamReaderGenerator(inputFileStreams);
        } catch (IOException e) {
            //TODO: Handle properly
            e.printStackTrace();
            generatorTmp = null;
            System.exit(1);
        }


        generator = generatorTmp;
    }


    /**
     * @return A copy of the combined flat reader
     */
    Reader distribute(){
        return generator.generate();
    }
}
package moss.project;

import org.junit.Test;
import org.junit.Before; 
import org.junit.After;

import java.io.Reader;


public class ProjectFlatReaderDistributorTest { 

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: distribute() 
* @throws Exception All thrown exceptions
*/ 
@Test
public void testDistribute() throws Exception { 
//TODO: Test goes here...
    ProjectFlatReaderDistributor distributor = new ProjectFlatReaderDistributor(TestObjects.TEST_PROJECT_1_PATH,
            PathFilter.TXT_FILTER);
    Reader reader = distributor.distribute();
    assert(reader.read() == 'c');
} 


} 
package moss.project;

import moss.algorithm.ComparisonStrategy;

import java.io.IOException;

/**
 * A class containing many operations that can be done on <i>Project</i> objects
 */
@SuppressWarnings("WeakerAccess")
public final class Projects {

    private Projects(){} //cannot be instantiated
    /**
     * Compares two <i>Project</i>s and returns a score from 0.0 to 1.0
     * @param first First project to be compared
     * @param second Second project to be compared
     * @param strategy Algorithm to be used for comparison
     * @return Score for comparison
     */
    public static double compare(Project first,
                                 Project second, ComparisonStrategy strategy) {
        try {
            return strategy.compare(first.getConcatenatedReader(), second.getConcatenatedReader());
        } catch (IOException e) {
            return 0.0; //CHANGE: The protocol when a file cannot be read is to just return a score of 0.0
        }
    }


}
package moss.projectpairmachine;

import moss.project.Project;

import java.util.*;

/**
 * Contains results for multi-project comparisons
 */
public final class ProjectsCorrelationMatrix implements Iterable<ProjectsCorrelationMatrix.ResultRow> {
    //CHANGE: changed class from package-private to public because the data needs to be accessible everywhere in the program.
    //The only real restriction is that this should only be constructable within the package
    private final Collection<ResultRow> rows;

    private ProjectsCorrelationMatrix(Collection<ResultRow> rows){
        this.rows = rows;
    }


    /**
     * @return A collection of <b>ResultRow</b>s, which contain immutable information about each row of results
     */
    final public Collection<ResultRow> getRows(){
        return rows;
    }

    /**
     * @return An iterator for result rows
     */
    @Override
    public Iterator<ResultRow> iterator() {
        return rows.iterator();
    }


    /**
     * @return The project names of all projects in the matrix
     */
    public final Collection<String> getProjectNames(){
        Collection<String> names = new ArrayList<>();
        for (ResultRow row : rows){
            names.add(row.getProject().getName());
        }
        return names;

    }
    /**
     * Holds information on the compared project and results for a single row of comparison
     */
    static public final class ResultRow{
        //CHANGE: Changed to public to allow results to be referenced everywhere
        private final Project rowProject;
        private final ResultSet results;

        private ResultRow(Project project, ResultSet results){
            this.rowProject = project;
            this.results = results;
        }


        /**
         * @return Results present in row
         */
        public final ResultSet getResults() {
            return results;
        }

        /**
         * @return Project associated with this row
         */
        public final Project getProject() {
            return rowProject;
        }
    }

    /**
     * Contains the results of a single project against all projects
     */
    static public final class ResultSet implements Iterable<ResultSet.ResultRecord> {
        //CHANGE: Changed to public to allow objects to be referenced everywhere
        //this class does not know about the existence of the project every other project here was compared to.
        //this is knowledge that the instantiator of the correlation matrix must track himself
        private Collection<ResultRecord> results = new ArrayList<>();
        /**
         * Collects all results of a comparison before compiling them into an immutable results object
         */
        private ResultSet(){
            //private to prevent instantiation from
        }

        /**
         * @param resultRecord result record to be added to the set
         */
        private void add(ResultRecord resultRecord){
            results.add(resultRecord);
        }

        /**
         * @param project Project to be checked
         * @return True if there is already a result for the given project
         */
        public boolean containsResultFor(Project project){
            for (ResultRecord record : results){
                if (record.getProject().equals(project)) return true;
            }
            return false;
        }



        /**
         * @return Returns the iterator for this set
         */
        @Override
        public final Iterator<ResultRecord> iterator() {
            return results.iterator();
        }


        /**
         * Contains a single result of a comparison.
         */
        static public final class ResultRecord {
            //CHANGE: Changed to public to allow referencing everywhere
            private final Project projectComparedAgainst;
            private final double score;

            private ResultRecord(Project projectComparedAgainst, double score) {
                this.projectComparedAgainst = projectComparedAgainst;
                this.score = score;
            }

            public final Project getProject() {
                return projectComparedAgainst;
            }

            public double getScore() {
                return score;
            }
        }
    }


    /**
     * Helps build the matrix containing comparison results
     */
    static class ProjectsCorrelationMatrixBuilder {
        //I have made explicit use of a linked hash map here because it maintains the order things were put in
        private LinkedHashMap<Project, ResultSet> resultTable = new LinkedHashMap<>();

        /**
         * @return The matrix built from the added results
         */
        ProjectsCorrelationMatrix createMatrix(){
            //the only role of this function is to create the matrix by taking the values in the result table
            //and putting it in a collection of result rows, the format the actual matrix uses
            Collection<ResultRow> rows = new ArrayList<>();
            for (Map.Entry<Project, ResultSet> resultTableEntry : resultTable.entrySet()){
                rows.add(new ResultRow(resultTableEntry.getKey(), resultTableEntry.getValue()));
            }
            return new ProjectsCorrelationMatrix(rows);

        }
        /**
         * [HELPER FUNCTION]
         * Adds a result record in only one direction of comparison
         *
         * @param projectComparedTo      project where the result is added
         * @param projectComparedAgainst project that the other project was compared against
         * @param result                 result of their comparison
         */
        private void addResultOneWay(Project projectComparedTo, Project projectComparedAgainst, double result) {
            //this will add the result to the table asymmetrically
            resultTable.putIfAbsent(projectComparedTo, new ResultSet());
            ResultSet projectResults = resultTable.get(projectComparedTo);

            //PHASE 2: If this comparison is already in the table, we will refrain from putting it again
            if (projectResults.containsResultFor(projectComparedAgainst)) return;


            projectResults.add(new ResultSet.ResultRecord(projectComparedAgainst, result));
        }


        /**
         * Adds result to table symmetrically
         *
         * @param firstProjectCompared  First project compared
         * @param secondProjectCompared Second project compared
         * @param result                Result of their comparison
         */
        final void addRecordIfNone(Project firstProjectCompared, Project secondProjectCompared, double result) {
            //TWO-WAY adding
            //All operations are only done on a single direction
            this.addResultOneWay(firstProjectCompared, secondProjectCompared, result);
            this.addResultOneWay(secondProjectCompared, firstProjectCompared, result);
        }
    }

}
package moss.project;

import moss.algorithm.TokenHashingStrategy;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After; 


public class ProjectsTest {

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: compare(Project first, Project second, ComparisonStrategy strategy)
 * @throws Exception All caught exceptions
* 
*/ 
@Test
public void testCompare() throws Exception { 
//TODO: Test goes here...
    ProjectBuilder projectBuilder = new ProjectBuilder();
    Project project1 = projectBuilder.setName("Test").setPath(TestObjects.TEST_PROJECT_1_PATH).createProject();
    Project project2 = projectBuilder.setName("Test_2").setPath(TestObjects.TEST_PROJECT_2_PATH).createProject();
    double comparison = Projects.compare(project1, project2, new TokenHashingStrategy());
    assert(comparison >= 0.0 && comparison <= 1.0);
    System.out.println("Test result:" + comparison);
} 


} 
package moss.gui.MultiProjectMenu;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;

/**
 * A model for the separate project option
 */
@SuppressWarnings("WeakerAccess")
public final class SeparateProjectMenuModel {
    private Collection<Path> projectPaths = new ArrayList<>();


    /**
     * @param projectPath Project path to be added
     */
    public void addProject(Path projectPath){
        projectPaths.add(projectPath);
    }

    /**
     * @return Collection of project paths collected for processing
     */
    public Collection<Path> retrieveProjectPaths(){
        return projectPaths;
    }
}
package moss.projectpairmachine;

import moss.algorithm.ComparisonStrategy;
import moss.project.*;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.stream.Stream;

/**
 * Compares multiple projects pair-wise
 */
public final class SimpleMultiProjectComparison implements MultiProjectComparison {
    //CHANGE: Changed to public so that other classes can make use of this
    /*
    TODO: Issues with the new refactor:
      1. With the new abstractions, the matrix is, for some reason, not entirely symmetrical
     */
    private final ComparisonStrategy strategy;

    /**
     * @param strategy Algorithm to be used for comparison
     * @return A comparison device for comparing projects with the given strategy
     */
    public static SimpleMultiProjectComparison fromStrategy(ComparisonStrategy strategy){
        //The static class will serve as the only way to make this class.
        //TODO: Since this class is stateless, consider using a hashtable to map strategies to already-built classes
        return new SimpleMultiProjectComparison(strategy);
    }

    /**
     * @param strategy Algorithm to be used for comparison
     */
    SimpleMultiProjectComparison(ComparisonStrategy strategy) {
        //CHANGE: This class will no longer have the ability to create projects; instead,
        //a new object dependency (MultiProjectStorage) will be used to contain all the projects
        //before it is sent here
        this.strategy = strategy;
    }


    /**

     */
    @Override
    public ProjectsCorrelationMatrix compareAll(MultiProjectStorage projects){
        //CHANGE: The result of this comparison has been refactored to a single less verbose class for correlation matrices
        //         This is to reduce the verbosity of the results' type
        ProjectsCorrelationMatrix.ProjectsCorrelationMatrixBuilder results =
                new ProjectsCorrelationMatrix.ProjectsCorrelationMatrixBuilder();
        Collection<Project> completedProjects = new ArrayList<>();
        for (Project firstProject : projects){
            for (Project secondProject : projects){
                if (completedProjects.contains(secondProject)) continue;
                results.addRecordIfNone(firstProject, secondProject,
                        Projects.compare(firstProject, secondProject, strategy));
            }
            completedProjects.add(firstProject);
        }

        return results.createMatrix();
    }
    /**
     * @param project Project to be compared against all stored projects
     * @param allProjects Other projects to be compared against <i>project</i>
     * @return A hashtable containing the MOSS of the passed project against all projects stored in this class
     */
    private Hashtable<Project, Double> compareAgainstAll(Project project, MultiProjectStorage allProjects){
        Hashtable<Project, Double> scores = new Hashtable<>();
        for (Project comparedProject : allProjects){
            //Projects.compare (a static class) is called here to get the score of the comparison against the passed project
            scores.putIfAbsent(comparedProject,
                    Projects.compare(project, comparedProject, strategy));
        }
        return scores;
    }

//    public Hashtable<Path, Double> compareAgainstAll(Path path){
//        for (Map.Entry<Path, ProjectFlatReaderDistributor> distributorEntry : projects.entrySet()){
//
//        }
//    }


}
package moss.projectpairmachine;

import moss.algorithm.TokenHashingStrategy;
import moss.project.*;
import org.junit.Test;


public class SimpleMultiProjectComparisonTest {

    @Test
    public void compareAgainstAll() {

    }


    @Test
    public void module0SubmissionsTest(){
        MultiProjectStorage storage =
                MultiProjectStorage.projectsIn(TestObjects.SUBMISSIONS_PATH, PathFilter.CPP_AND_JAVA_FILTER);

        SimpleMultiProjectComparison simpleMultiProjectComparison = new SimpleMultiProjectComparison(new TokenHashingStrategy());
        ProjectsCorrelationMatrix matrix = simpleMultiProjectComparison.compareAll(storage);
        for (ProjectsCorrelationMatrix.ResultRow row : matrix){
            System.out.print(row.getProject().getName());
            for (ProjectsCorrelationMatrix.ResultSet.ResultRecord score : row.getResults()){
                System.out.printf("\t%.4f", score.getScore());
            }
            System.out.println();
        }
    }
}
package moss.algorithm;

import moss.project.PathFilter;
import moss.project.Project;
import moss.project.ProjectBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Path;

/**
 * Takes in a project object
 * and allows the user to compute
 * different metrics for that project
 * based on Haelstead's software metrics.
 */
@SuppressWarnings("WeakerAccess")
final public class SoftwareMetrics{
    private final Project measuredProject;
    private final TokenClusterOccurrenceTable table;


    /**
     * @param project Project to be measured
     * @return Software metric object from the project
     */
    public static SoftwareMetrics fromProject(Project project) throws IOException {
        return new SoftwareMetrics(project);
    }

    /**
     * @param path Path of the project
     * @param filters Filters to be applied to that path
     * @return Software metric object from the project
     */
    public static SoftwareMetrics fromPath(Path path, PathFilter filters) throws IOException {
        ProjectBuilder projectBuilder = new ProjectBuilder();
        projectBuilder
                .setPath(path)
                .setName(path.toString())
                .setFilter(filters);
        return SoftwareMetrics
                .fromProject(projectBuilder.createProject());
    }



    /**
     * @param project Project to be tested for metrics
     * @throws IOException If project reader cannot be read
     */
    private SoftwareMetrics(Project project) throws IOException {
        this.measuredProject = project;
        table = new TokenClusterOccurrenceTable();
        table.tabulate(project.getConcatenatedReader());
    }

    /**
     * @return The volume of the project
     */
    public final double volume() {
        return table.total() * Math.log(table.uniqueCount());
    }



}
package moss.project;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

@SuppressWarnings("WeakerAccess")
public class TestObjects {
    static public final Path TEST_PROJECT_1_PATH = Paths.get(new File("testfiles/project1").getAbsolutePath());
    static public final Path TEST_PROJECT_2_PATH = Paths.get(new File("testfiles/project2").getAbsolutePath());
    static public final Path SUBMISSIONS_PATH = Paths.get(new File("test_submissions").getAbsolutePath());
    static public final int SUBMISSION_PATH_FOLDER_COUNT = 36; //TODO: automate number

}
package moss.algorithm;

import java.util.Objects;

@SuppressWarnings("unused")
public class Token {
    public enum TYPE{
        EOL, NUMBER, WORD, STRING_LITERAL, IGNORE, OTHER, NONE
    }


    private final int id;
    private final int lineNo;
    @SuppressWarnings("FieldCanBeLocal")
    private final Object value;

    @SuppressWarnings("WeakerAccess")
    public static class TokenBuilder {
        private int id;
        private int lineNo;
        private Object value;
        private TYPE type;

        /**
         *
         * @param type token type of the token to be built
         * @return returns the called builder (Stream)
         * @see TYPE
         */
        @SuppressWarnings({"SameParameterValue", "UnusedReturnValue"})
        final TokenBuilder setType(TYPE type){
            return setTypeWithValue(type, null);
        }

        /**
         * @param type token type of the token to be built
         * @param value underlying value of the token (e.g. a number for number tokens)
         * @param <T> specifies the type of <i>value</i>
         * @return returns the called builder (Stream)
         * @see TYPE
         */
        final <T> TokenBuilder setTypeWithValue(TYPE type, T value){
            this.type = type;
            this.value = value;
            //NOTE: this null check is necessary because if there is no SPECIFIC value for a particular token,
            //it is unnecessary to separate its identity from other already existing tokens of precisely the same type
            if (this.value == null)
                this.id = this.type.hashCode();
            else
                this.id = this.value.hashCode();
            return this;
        }


        /**
         * @param lineNo Line number of the token to be built
         * @return returns the called builder (Stream)
         */
        TokenBuilder setLineNo(int lineNo) {
            this.lineNo = lineNo;
            return this;
        }


        /**
         *
         * @return returns a built token with the entered values
         *
         */
        public Token createToken() {
            return new Token(id, lineNo, value);
        }
    }


    private Token(int hash, int lineNo, Object value) {
        this.id = hash;
        this.lineNo = lineNo;
        this.value = value;
    }

    /**
     * @return returns the token's line number
     */
    public final int getLineNo() {
        return lineNo;
    }

    /**
     * @return returns the auto-generated identifier of the token
     */
    public final int getId() {
        return id;
    }

    /**
     * @param other token being compared to
     * @return equality of the tokens (based on their Id)
     */
    @Override
    public final boolean equals(Object other){
        //TODO: Write motivation for using only id as the identifier of the class
        if (!(other instanceof Token)) return false;
        Token ref = (Token)other;
        return ref.id == this.id;
    }

    /**
     * The hash code is based on the hash of the token's ID.
     * This has the consequence of two non-valued tokens getting the same ID
     * @return hash code of the token
     */
    @Override
    public final int hashCode() {
        return Objects.hash(id);
    }





}
package moss.algorithm;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * Used to hold a sequential group of tokens
 */
class TokenCluster {
    private final Collection<Token> tokens;

    /**
     * @param o cluster being compared with
     * @return equality of the two clusters based on the equality of each token in them
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TokenCluster that = (TokenCluster) o;
        return tokens.equals(that.tokens);
    }

    /**
     * @return combined hash code of all the <i>Token</i>s in the cluster
     */
    @Override
    public int hashCode() {
        return Objects.hash(tokens);
    }

    /**
     * The TokenCluster class is meant to be immutable upon construction,
     * so adding of tokens will and can only be done through this builder. You can add Token(s) sequentially in this Builder.
     */
    static class TokenClusterBuilder{
        private Collection<Token> tokens;
        TokenClusterBuilder(){ this.tokens = new ArrayList<>(); }

        /**
         * This will bring <b>tok</b> to the end of the cluster
         * @param tok token to be added to the cluster
         */
        void addNext(Token tok){ tokens.add(tok); }

        /**
         * @return a cluster of all the added tokens
         */
        TokenCluster cluster(){ return new TokenCluster(tokens); }
    }

    /**
     * @param tokens Container for tokens to be placed in the cluster. Holds the tokens in the proper sequence
     */
    private TokenCluster(Collection<Token> tokens){
        this.tokens = tokens;
    }
}
package moss.algorithm;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;

@SuppressWarnings("WeakerAccess")
class TokenClusterOccurrenceTable {
    private HashMap<TokenCluster, Integer> occurrences;
    static int DEFAULT_CLUSTER_SIZE = 2;

    /**
     * Creates the table
     */
    TokenClusterOccurrenceTable(){
        occurrences = new HashMap<>();
    }


    /**
     * Adds the token cluster to the table of clusters that have occurred (if absent) or adds one to the
     * occurrence count of that cluster if present
     * @param tokenCluster Cluster to be added
     */
    //adds the token to the occurrences table. Puts it in if the token has yet to occur
    private void addOccurred(TokenCluster tokenCluster){
        occurrences.putIfAbsent(tokenCluster, 0);
        occurrences.put(tokenCluster,
                occurrences.get(tokenCluster) + 1);
    }

    /**
     * Adds all the tokens from a reader to the table
     * @param reader source of tokens to be added to the table
     * @throws IOException Thrown if there is a problem communicating with <b>reader</b>.
     */
    void tabulate(Reader reader) throws IOException {
        HashingTokenizer tokenizer = new HashingTokenizer(reader);
        for (TokenCluster cluster : tokenizer.remainingTokenClusters(DEFAULT_CLUSTER_SIZE)){
            this.addOccurred(cluster);
        }
    }


    /**
     * @return The number of unique tokens that have been tabulated more than once.
     */
    final int collisionCount(){
        //CHANGE: Changed to final to prevent overriding
        int count = 0;
        for (Integer singleCount : this.occurrences.values()){
            if (singleCount >= 2) count++;
        }
        return count;
    }


    /**
     * @return Number of unrepeated tokens
     */
    final int uniqueCount(){
        return this.total() - this.collisionCount();
    }

    /**
     * @return The total number of unique tokens that have been tabulated.
     */
    //quick delegation
    final int total(){
        //CHANGE: Changed to final to prevent overriding
        //NOTE: Upon testing, there seems to be cases for which this might return 0 for some strange reason
        if (this.occurrences.size() == 0) System.err.println("Error detected");
        return this.occurrences.size();
    }



}
package moss.algorithm;

import java.io.IOException;
import java.io.Reader;

/**
 * An algorithm that compares two readers based
 * on their component tokens using N-Gram comparisons with token hashing
 */
public class TokenHashingStrategy implements ComparisonStrategy {


    /**
     * [CHANGE] Deprecation notice has been removed. The strategy for all such instances of a ComparisonStrategy
     * is that they should be contractually obligated to be in a state where they are reusable after use
     */
    public TokenHashingStrategy(){

    }


    /**
     * @param str1 First reader to be compared
     * @param str2 Second reader to be compared
     * @return A number between 0.0 and 1.0 that indicates the percentage of closeness of the two readers based on their tokens
     * @throws IOException Thrown when one of the readers fail
     */
    //returns a score between 0.0 and 1.0
    @Override
    public Double compare(Reader str1, Reader str2) throws IOException {
        //CHANGE: The token cluster tables have been converted to a local variable to allow reusability of this class
        TokenClusterOccurrenceTable table = new TokenClusterOccurrenceTable();
        table.tabulate(str1);
        table.tabulate(str2);
        return (double) table.collisionCount() / (double) table.total();
    }


}
package moss.algorithm;

class TokenTest {
    public static void main(String[] args){
//        Token token1 = new Token.TokenBuilder().setID(123).setLineNo(1).createToken();
//        Token token2 = new Token.TokenBuilder().setID(1234).setLineNo(1).createToken();
//        Token token3 = new Token.TokenBuilder().setID(1234).setLineNo(1).createToken();
//        System.out.println(token1.hashCode());
//        System.out.println(token2.hashCode());
//        System.out.println(token3.hashCode());
    }
}
