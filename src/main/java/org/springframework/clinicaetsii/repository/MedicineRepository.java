/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package org.springframework.clinicaetsii.repository;

import java.util.Collection;
import org.springframework.clinicaetsii.model.Medicine;
import org.springframework.dao.DataAccessException;

public interface MedicineRepository {

	Collection<Medicine> findAll() throws DataAccessException;

	Medicine findById(int id) throws DataAccessException;

	Medicine save(Medicine medicine) throws DataAccessException;

	void delete(Medicine medicine) throws DataAccessException;
	
	void deleteAll() throws DataAccessException;

}
