/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.ui.externaltools.internal.variables;

import org.eclipse.core.variables.IDynamicVariable;
import org.eclipse.core.variables.IDynamicVariableResolver;
import org.eclipse.ui.externaltools.internal.model.ExternalToolBuilder;


public class BuildTypeResolver implements IDynamicVariableResolver {

	/* (non-Javadoc)
	 * @see org.eclipse.debug.internal.core.stringsubstitution.IContextVariableResolver#resolveValue(org.eclipse.debug.internal.core.stringsubstitution.IContextVariable, java.lang.String)
	 */
	public String resolveValue(IDynamicVariable variable, String argument) {
			return ExternalToolBuilder.getBuildType();
	}
}
