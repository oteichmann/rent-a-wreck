/**
 * 
 */
package com.prodyna.pac.rentawreck.backend.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.jboss.arquillian.junit.InSequence;
import org.junit.Test;

import com.prodyna.pac.rentawreck.backend.common.model.AbstractEntity;
import com.prodyna.pac.rentawreck.backend.common.service.AbstractEntityPersistenceService;

/**
 * AbstractEntityCRUDTest
 *
 * @author Oliver Teichmann
 *
 */
public abstract class AbstractEntityCRUDTest<T extends AbstractEntity> {
	
	protected abstract AbstractEntityPersistenceService<T> getService();
	
	protected abstract T createCRUDEntity();
	protected abstract T updateCRUDEntity(T persitedEntity);

	@Test
	@InSequence(0)
//	@Transactional(TransactionMode.ROLLBACK)
	public void testCRUDOperations(){
		
		T entity = createCRUDEntity();
		assertNull(getService().read(entity.getUuid()));

		T persitedEntity = getService().create(entity);
		assertEquals(entity.getUuid(), persitedEntity.getUuid());
		assertsAfterCreate(persitedEntity);

		assertNotNull(getService().read(entity.getUuid()));
		
		T updatedEntity = updateCRUDEntity(persitedEntity);
		updatedEntity = getService().update(updatedEntity);
		assertEquals(entity.getUuid(), updatedEntity.getUuid());
		assertsAfterUpdate(updatedEntity);
		
		getService().delete(entity.getUuid());
		assertNull(getService().read(entity.getUuid()));
	}
	
	/**
	 * This method can be overridden for custom test case asserts.
	 * @param persitedEntity 
	 */
	protected void assertsAfterCreate(T persitedEntity) {
		return;
	}

	/**
	 * This method can be overridden for custom test case asserts.
	 * @param updatedEntity 
	 */
	protected void assertsAfterUpdate(T updatedEntity) {
		return;
	}
}
