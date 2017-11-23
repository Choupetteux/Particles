package particles;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class ParticlesController {
	@FXML 
	private Label label;
	@FXML
	private Canvas canvas;

	private ArrayList<Particle> particles = new ArrayList<>();

	private ExecutorService pool = Executors.newCachedThreadPool();



	/**
	 * retourne le canvas dans lequel les particules doivent être dessinées
	 * @return the canvas
	 */
	public Canvas getCanvas() {
		return this.canvas;
	}

	/**
	 * ajoute une nouvelle particule (addParticle) à  la liste et met à  jour le texte du label.
	 */
	@FXML
	public void onAdd() {
		this.addParticle();
		this.label.setText(this.particles.size() + " particules");

	}

	/**
	 * ajoute une nouvelle particule à  la liste et la soumet au pool
	 */
	public void addParticle() {
		Particle particle = new Particle(this);
		this.particles.add(particle);
		pool.submit(particle);
	}

	/**
	 * retire une particule de la liste
	 * 
	 * @param p
	 *            particule Ã  retirer
	 */
	public void removeParticle(Particle p) {
		this.particles.remove(p);   	

	}

	/**
	 * efface le canvas (dessin d'un rectangle noir) et dessine les particules de la liste (par appel de leur méthode draw).
	 */
	public void drawParticles() {
		Platform.runLater(new Runnable(){
			@Override
			public void run() {
				canvas.getGraphicsContext2D().setFill(Color.BLACK);
				canvas.getGraphicsContext2D().fillRect(0,0,200,200);
				for(Particle particle : particles){
					particle.draw();
				}
			}
		});
	}

}
