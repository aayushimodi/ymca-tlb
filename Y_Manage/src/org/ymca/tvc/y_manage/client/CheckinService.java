package org.ymca.tvc.y_manage.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface CheckinService extends RemoteService {
	String checkinStudent(String studentName) throws IllegalArgumentException;
}
