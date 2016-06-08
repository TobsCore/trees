package studium.computergrafik.trees;

import javafx.fxml.FXMLLoader;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

public class Controller {
	
	private static final double ROTATE_TICK = 10.0;

	private double mouseXOld;
	private double mouseYOld;
	private double mouseXCurr;
	private double mouseYCurr;
	private double mouseXDelta;
	private double mouseYDelta;
	
	private Scene scene;
    private Stage primaryStage;
	private Rotate cameraRotateX = new Rotate(-30.0, Rotate.X_AXIS);
	private Rotate cameraRotateY = new Rotate(-185.0, Rotate.Y_AXIS);
	private Translate cameraTranslate = new Translate(5.0, 2.0, -295.97);
	
	public Controller(Scene scene) {
		this.scene = scene;
		scene.setCamera(setUpCamera());
		
		addKeyHandlers();
		addMouseHandlers();
	}

    public Controller(Scene scene, Stage primaryStage) {
        this(scene);
        this.primaryStage = primaryStage;
    }

    private final Camera setUpCamera() {
		PerspectiveCamera camera = new PerspectiveCamera(true);
		
		camera.setFieldOfView(40.0);
		camera.setFarClip(10000.0);
		camera.setRotationAxis(Rotate.Z_AXIS);
		camera.setRotate(180.0);
		camera.getTransforms().addAll(cameraRotateY, cameraRotateX,
				cameraTranslate);
		
		return camera;
	}
	
	private final void addKeyHandlers() {
		scene.setOnKeyPressed(event -> {
			switch(event.getCode()) {
			case RIGHT:
				cameraRotateY.setAngle(cameraRotateY.getAngle() + ROTATE_TICK);
				break;
			case LEFT:
				cameraRotateY.setAngle(cameraRotateY.getAngle() - ROTATE_TICK);
				break;
			case UP:
				cameraRotateX.setAngle(cameraRotateX.getAngle() + ROTATE_TICK);
				break;
			case DOWN:
				cameraRotateX.setAngle(cameraRotateX.getAngle() - ROTATE_TICK);
				break;
            case SPACE:
                    createTree();
                    break;
			default:
				// For the sake of conventions.
				break;
			}
		});
	}

    private void createTree() {
        double randomXPosition = (Math.random() * 200) - 100;
        double randomZPosition = (Math.random() * 200) - 100;
        try {
            Group tree = (Group) FXMLLoader.load(getClass().getResource(
                    "Tree.fxml"));
            tree.setTranslateX(randomXPosition);
            tree.setTranslateZ(randomZPosition);
            tree.setScaleX(0.3);
            tree.setScaleZ(0.3);
            tree.setScaleY(0.3);
            tree.setTranslateY(tree.getTranslateY()-53);

            ((Group) scene.getRoot()).getChildren().add(tree);
        } catch (Exception e) {
            e.printStackTrace();
        }

       // printCameraPosition();
    }

    private void printCameraPosition() {
        System.out.println("Rotate X: " + cameraRotateX.getAngle());
        System.out.println("Rotate Y: " + cameraRotateY.getAngle());
        System.out.println("Translate X: " + cameraTranslate.getX());
        System.out.println("Translate Y: " + cameraTranslate.getY());
        System.out.println("Translate Z: " + cameraTranslate.getZ());
    }

    private final void addMouseHandlers() {
        scene.setOnMousePressed(event -> {
            mouseXCurr = event.getSceneX();
            mouseYCurr = event.getSceneY();
            mouseXOld = event.getSceneX();
            mouseYOld = event.getSceneY();
        });

        scene.setOnMouseDragged(event -> {
            mouseXOld = mouseXCurr;
            mouseYOld = mouseYCurr;
            mouseXCurr = event.getSceneX();
            mouseYCurr = event.getSceneY();
            mouseXDelta = mouseXCurr - mouseXOld;
            mouseYDelta = mouseYCurr - mouseYOld;

            cameraTranslate.setX(cameraTranslate.getX() - mouseXDelta);
            cameraTranslate.setY(cameraTranslate.getY() - mouseYDelta);
        });

        scene.setOnScroll(event -> {
        	cameraTranslate.setZ(cameraTranslate.getZ() + event.getDeltaY());
        });
	}

}
