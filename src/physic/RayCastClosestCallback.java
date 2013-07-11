package physic;
// This test demonstrates how to use the world ray-cast feature.
// NOTE: we are intentionally filtering one of the polygons, therefore
// the ray will always miss one type of polygon.

import logic.entity.GameObject;
import logic.entity.ship.Ship;
import org.jbox2d.callbacks.RayCastCallback;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.Fixture;

// This callback finds the closest hit. Polygon 0 is filtered.
public class RayCastClosestCallback implements RayCastCallback {

    private boolean visible;
    private Vec2 m_point;
    private Vec2 m_normal;


    public RayCastClosestCallback() {
        visible = false;
    }

    public float reportFixture(Fixture fixture, Vec2 point, Vec2 normal, float fraction) {


        Body body = fixture.getBody();
        Object userData = body.getUserData();
        if (userData == null) {
            visible = false;
            return 0;
        } else {
            String nameObjectOne = ((GameObject) userData).getMyClassName();
            if (nameObjectOne == null) {
                visible = false;
                return 0;
            }
            String nameObjectTwo = Ship.CLASS_NAME;
            /*
            System.out.println("**");
            System.out.println(Ship.CLASS_NAME);
            System.out.println(((GameObject) userData).getMyClassName());
            System.out.println("**");
            */
            if (!nameObjectOne.equals(nameObjectTwo)) {
                visible = false;
                return 0;
            }
        }

        visible = true;
        m_point = point;
        m_normal = normal;
        return fraction;
        //

                                           /*
        Body body = fixture.getBody();
        Object userData = body.getUserData();
        if (userData != null) {
            System.out.println("[");
            System.out.println(Ship.CLASS_NAME);
            System.out.println(((GameObject) userData).getMyClassName());
            System.out.println("]");
            String nameObjectOne = ((GameObject) userData).getMyClassName();
            String nameObjectTwo = Ship.CLASS_NAME;
            if (nameObjectOne == null) {
                visible = true;
                m_point = point;
                m_normal = normal;
                return -1f;
            }
            if (nameObjectOne.equals(nameObjectTwo)) {
                visible = true;
                m_point = point;
                m_normal = normal;
                return fraction;
            } else {
                return fraction;
            }
        }
        visible = true;
        m_point = point;
        m_normal = normal;
        return fraction;                     */
    }

    public boolean isVisible() {
        return visible;
    }
};