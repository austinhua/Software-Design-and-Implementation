package auth_environment.delegatesAndFactories;

import auth_environment.Models.UnitView;
import auth_environment.Models.Interfaces.IMapPane;
import auth_environment.Models.Interfaces.IPathTabModel;
import auth_environment.view.PathPoint;
import auth_environment.view.UnitPicker;
import game_engine.properties.Movement;
import game_engine.properties.Position;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

/**
 * Created by BrianLin on 4/11/2016
 * Team member responsible: Brian
 * @author Xander, Brian
 * This delegate class adds dragging functions. Woowee dongerino. 
 */

public class DragDelegate {

	public void addUnitViewSource(UnitView source) {
		
		source.setOnDragDetected(event -> {
			Dragboard db = source.startDragAndDrop(TransferMode.COPY);
			ClipboardContent content = new ClipboardContent();
			source.setId(this.getClass().getSimpleName() + System.currentTimeMillis());
			content.putImage(source.getImage());
			content.putString(source.getId());
			db.setContent(content);
			event.consume();
		});
		
		source.setOnDragDone(event -> event.consume());

	}

	public void setUpNodeTarget(IMapPane target, UnitPicker myPicker) {

		Pane targetPane = target.getRoot();
		
		targetPane.setOnDragOver(event -> {
			event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
			event.consume();
		});
		
		targetPane.setOnDragEntered(event -> event.consume());
		
		targetPane.setOnDragExited(event -> event.consume());
		
		targetPane.setOnDragDropped(event -> {
			event.acceptTransferModes(TransferMode.COPY);
			Dragboard db = event.getDragboard();
			boolean success = false;
			if (db.hasString()) {
				UnitView imv = ((UnitView)(myPicker.getRoot().lookup("#" + db.getString()))).clone();
				target.adjustUnitViewScale(imv);
				target.adjustUnitViewXY(imv, event.getSceneX(), event.getSceneY());
				target.addToPane(imv);
				target.getModel().addTerrain(imv.getX(), imv.getY(), imv.getUnit());
				success = true;
			}
			event.setDropCompleted(success);
			event.consume();
		});

	}
	
	public void setUpNodeTarget(PathPoint pathPoint, UnitPicker picker, UnitPicker picker0, IPathTabModel pathModel) {
		Circle target = pathPoint.getCircle(); 
		
		target.setOnDragOver(e -> {
			e.acceptTransferModes(TransferMode.COPY_OR_MOVE);
			e.consume();
		});
		
		target.setOnDragEntered(e -> e.consume());

		target.setOnDragExited(e -> e.consume());
		
		target.setOnDragDropped(e -> {
			e.acceptTransferModes(TransferMode.COPY);
			Dragboard db = e.getDragboard();
			boolean success = false;
			if (db.hasString()) {
				UnitView uv = new UnitView(); 
				if (((UnitView)(picker.getRoot().lookup("#" + db.getString())))!=null) {
					uv = (UnitView)(picker.getRoot().lookup("#" + db.getString()));
				}
				else {
					uv = (UnitView)(picker0.getRoot().lookup("#" + db.getString()));
				}
				pathModel.setActiveUnit(uv.getUnit());
				Position pos = pathPoint.getPosition(); 
				pathModel.addSpawnToActiveLevel(pos);
				uv.getUnit().getProperties().setMovement(new Movement(pos));
				uv.getUnit().getProperties().setPosition(pos);
				success = true;
			}
			e.setDropCompleted(success);
			e.consume();
		});
	}
}
