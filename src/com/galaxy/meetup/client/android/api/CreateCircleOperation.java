/**
 * galaxy inc.
 * meetup client for android
 */
package com.galaxy.meetup.client.android.api;

import java.io.IOException;

import android.content.Context;
import android.content.Intent;

import com.galaxy.meetup.client.android.content.EsAccount;
import com.galaxy.meetup.client.android.content.EsPeopleData;
import com.galaxy.meetup.client.android.network.PlusiOperation;
import com.galaxy.meetup.client.android.network.http.HttpOperation;
import com.galaxy.meetup.server.client.domain.request.CreateCircleRequest;
import com.galaxy.meetup.server.client.domain.response.CreateCircleResponse;
import com.galaxy.meetup.server.client.v2.request.Request;
import com.galaxy.meetup.server.client.v2.response.Response;

/**
 * 
 * @author sihai
 *
 */
public class CreateCircleOperation extends PlusiOperation {
	
	private final String mCircleName;
    private final boolean mJustFollowing;
    
	public CreateCircleOperation(Context context, EsAccount esaccount, String s, boolean flag, Intent intent, HttpOperation.OperationListener operationlistener)
    {
        super(context, esaccount, "createcircle", intent, operationlistener, CreateCircleResponse.class);
        mCircleName = s;
        mJustFollowing = flag;
    }

    protected final void handleResponse(Response response) throws IOException
    {
        CreateCircleResponse createcircleresponse = (CreateCircleResponse)response;
        EsPeopleData.insertNewCircle(mContext, getAccount(), createcircleresponse.circleId.focusId, mCircleName, createcircleresponse.nameSortKey, mJustFollowing);
    }

    @Override
    protected final Request populateRequest()
    {
        CreateCircleRequest createcirclerequest = new CreateCircleRequest();
        createcirclerequest.name = mCircleName;
        String s;
        if(mJustFollowing)
            s = "JUST_FOLLOWING";
        else
            s = "INCLUDE_IN_YOUR_CIRCLES";
        createcirclerequest.justFollowingStatus = s;
        return createcirclerequest;
    }

}
