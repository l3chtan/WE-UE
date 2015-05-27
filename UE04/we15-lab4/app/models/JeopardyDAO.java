package models;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import play.Logger;
import play.db.jpa.JPA;

/**
 * Provides Data Access methods for JPA
 */
public class JeopardyDAO implements IGameDAO {
    public static final JeopardyDAO INSTANCE = new JeopardyDAO();

    private JeopardyDAO() { }
    
    /**
     * Get a given quiz user based on the id
     * @param id
     * @return
     */
    public JeopardyUser findById(long id) {
        return em().find(JeopardyUser.class, id);
    }

    public JeopardyUser findByUserName(String name) {
        if (name != null && !name.isEmpty()) {
            return getByUserName(name);
        } else {
            return null;
        }
    }

    /**
     * Get a given quiz user based on the name
     * @param name
     * @return
     */
    private JeopardyUser getByUserName(String name) {
        String queryStr = "from JeopardyUser where userName = :userName";
        TypedQuery<JeopardyUser> query = em().createQuery(queryStr,
                JeopardyUser.class).setParameter("userName", name);
        List<JeopardyUser> list = query.getResultList();
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }


    /**
     * Save an entity. Throws an error if an entity with the given id already exists
     * @param entity
     * @return
     */
    @Override
    public void persist(BaseEntity entity) {
        if (entity.getId() == null){
            em().persist(entity);
        Logger.info("New user added to database");
    } else {
        Logger.error("There is already entry using the same ID");
    }
        // TODO: Implement Method
        // throw new  UnsupportedOperationException("Not yet implemented.");
    }

/*    public void persist(JeopardyUser user){
        if(user.getId() != null && findById(user.getId()) != null) {
            return;
        }

        em().persist(user);
    }

    public void persist(Category category){

    }
    public void persist(Question question){

    }
    public void persist(Answer answer){

    }*/


    /**
     * If no entity with the given id exists in the DB, a new entity is stored. If there is already
     * an entity with the given id, the entity is updated
     * @param entity
     * @param <T>
     * @return
     */
    @Override
    public <T extends BaseEntity> T merge(T entity) {
        long id = entity.getId();

        if(em().find(Category.class,id)==null &&
                em().find(Question.class,id)==null &&
                em().find(Answer.class,id)==null &&
                findById(entity.getId()) == null){
            persist(entity);
        } else {
            em().merge(entity);
        }
        // TODO: Implement Method
        // throw new UnsupportedOperationException("Not yet implemented.");
        return entity;
    }

    /**user
     * Get an entity of the given type using the id
     * @param id
     * @param entityClazz
     * @param <T>
     * @return
     */
    @Override
    public <T extends BaseEntity> T findEntity(Long id, Class<T> entityClazz) {
        // TODO: Implement Method
        throw new UnsupportedOperationException("Not yet implemented.");
    }


    /**
     * Get a list of all entities of a certain type
     *
     * @param entityClazz
     * @param <E>
     * @return
     */
    @Override
    public <E extends BaseEntity> List<E> findEntities(Class<E> entityClazz) {
        // TODO: Implement Method
        throw new UnsupportedOperationException("Not yet implemented.");
    }

    /**
     * Get the entity manager
     * @return
     */
    private EntityManager em() {
        return JPA.em();
    }

}