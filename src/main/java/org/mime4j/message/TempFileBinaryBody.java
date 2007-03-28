/****************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one   *
 * or more contributor license agreements.  See the NOTICE file *
 * distributed with this work for additional information        *
 * regarding copyright ownership.  The ASF licenses this file   *
 * to you under the Apache License, Version 2.0 (the            *
 * "License"); you may not use this file except in compliance   *
 * with the License.  You may obtain a copy of the License at   *
 *                                                              *
 *   http://www.apache.org/licenses/LICENSE-2.0                 *
 *                                                              *
 * Unless required by applicable law or agreed to in writing,   *
 * software distributed under the License is distributed on an  *
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY       *
 * KIND, either express or implied.  See the License for the    *
 * specific language governing permissions and limitations      *
 * under the License.                                           *
 ****************************************************************/

package org.mime4j.message;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mime4j.util.TempFile;
import org.mime4j.util.TempPath;
import org.mime4j.util.TempStorage;


/**
 * Binary body backed by a {@link org.mime4j.util.TempFile}.
 *
 * 
 * @version $Id: TempFileBinaryBody.java,v 1.2 2004/10/02 12:41:11 ntherning Exp $
 */
class TempFileBinaryBody extends AbstractBody implements BinaryBody {
    private static Log log = LogFactory.getLog(TempFileBinaryBody.class);
    
    private Entity parent = null;
    private TempFile tempFile = null;

    public TempFileBinaryBody(InputStream is) throws IOException {
        
        TempPath tempPath = TempStorage.getInstance().getRootTempPath();
        tempFile = tempPath.createTempFile("attachment", ".bin");
        
        OutputStream out = tempFile.getOutputStream();
        IOUtils.copy(is, out);
        out.close();
    }
    
    /**
     * @see org.mime4j.message.AbstractBody#getParent()
     */
    public Entity getParent() {
        return parent;
    }
    
    /**
     * @see org.mime4j.message.AbstractBody#setParent(org.mime4j.message.Entity)
     */
    public void setParent(Entity parent) {
        this.parent = parent;
    }
    
    /**
     * @see org.mime4j.message.BinaryBody#getInputStream()
     */
    public InputStream getInputStream() throws IOException {
        return tempFile.getInputStream();
    }
    
    /**
     * @see org.mime4j.message.Body#writeTo(java.io.OutputStream)
     */
    public void writeTo(OutputStream out) throws IOException {
	IOUtils.copy(getInputStream(),out);
    }
}