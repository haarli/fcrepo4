/**
 * Copyright 2015 DuraSpace, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.fcrepo.serialization;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.fcrepo.kernel.models.FedoraResource;
import org.fcrepo.kernel.exception.InvalidChecksumException;
import org.springframework.stereotype.Component;

/**
 * Interface for serializing/deserializing a FedoraObject
 * to some transportable format
 *
 * @author cbeer
 */
@Component
public interface FedoraObjectSerializer {
    // Key for jcr/xml serialization
    final String JCR_XML = "jcr/xml";
    /**
     * Get the key for the serializer (that will be
     * used at the REST API to identify the format)
     * @return serializer key
     */
    String getKey();

    /**
     * Get the media type for the serialized output
     * (so we can send the right mime type as appropriate)
     * @return serializer output media type
     */
    String getMediaType();

    /**
     * Determines whether this FedoraObjectSerializer can be used to
     * serialize the given resource.  A FedoraObjectSerializer
     * implementation may use any arbitrary criteria to determine
     * which resources it can serialize, so this method should be
     * invoked to avoid an InvalidSerializationFormatException from
     * {@link #serialize}.
     * @param resource
     * @return
     */
    boolean canSerialize(final FedoraResource resource);

    /**
     * Serialize a FedoraObject into some format with options for recurse
     * and skipBinary, and write it to the given OutputStream
     *
     * @param obj
     * @param out
     * @param skipBinary
     * @param recurse
     * @throws RepositoryException
     * @throws IOException
     * @throws org.fcrepo.serialization.InvalidSerializationFormatException
     */
    void serialize(final FedoraResource obj, final OutputStream out, final boolean skipBinary, final boolean recurse)
                    throws RepositoryException, IOException, InvalidSerializationFormatException;

    /**
     * Read the given InputStream and de-serialize the content
     * into new nodes in the given session using the given path
     * as the parent node
     *
     * @param session
     * @param path
     * @param stream
     * @throws IOException
     * @throws RepositoryException
     * @throws InvalidChecksumException
     * @throws org.fcrepo.serialization.InvalidSerializationFormatException
     */
    void deserialize(final Session session, final String path,
            final InputStream stream) throws IOException, RepositoryException,
            InvalidChecksumException, InvalidSerializationFormatException;

}
