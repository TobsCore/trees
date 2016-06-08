package studium.computergrafik.trees;

import javafx.fxml.FXMLLoader;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

import java.io.IOException;

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
	private Rotate cameraRotateX = new Rotate(0.0, Rotate.X_AXIS);
	private Rotate cameraRotateY = new Rotate(45.0, Rotate.Y_AXIS);
	private Translate cameraTranslate = new Translate(0.0, -150.0, -700.0);
	
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
                    createEifelTower();
                    break;
			default:
				// For the sake of conventions.
				break;
			}
		});
	}

    private void createEifelTower() {
        double randomXPosition;
        double randomYPosition;
        try {
            Group eiffelTower = (Group) FXMLLoader.load(getClass().getResource(
                    "Eifeltower.fxml"));
            ((Group) scene.getRoot()).getChildren().add(eiffelTower);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
