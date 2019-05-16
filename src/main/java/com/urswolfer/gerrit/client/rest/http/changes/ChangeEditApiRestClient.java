package com.urswolfer.gerrit.client.rest.http.changes;

import com.google.common.base.Optional;
import com.google.common.collect.Iterables;
import com.google.gerrit.extensions.api.changes.ChangeEditApi;
import com.google.gerrit.extensions.restapi.BinaryResult;
import com.google.gerrit.extensions.restapi.NotImplementedException;
import com.google.gerrit.extensions.restapi.RawInput;
import com.google.gerrit.extensions.restapi.RestApiException;
import com.google.gson.JsonElement;
import com.urswolfer.gerrit.client.rest.http.GerritRestClient;
import com.urswolfer.gerrit.client.rest.http.util.BinaryResultUtils;
import java.io.IOException;
import org.apache.http.HttpResponse;

public class ChangeEditApiRestClient extends ChangeEditApi.NotImplemented implements ChangeEditApi {

    private final GerritRestClient gerritRestClient;
    private final ChangesRestClient changesRestClient;
    private final ChangeApiRestClient changeApiRestClient;
    private final ChangesParser changesParser;
    private final CommentsParser commentsParser;
    private final IncludedInInfoParser includedInInfoParser;
    private final FileInfoParser fileInfoParser;
    private final DiffInfoParser diffInfoParser;
    private final AddReviewerResultParser addReviewerResultParser;
    private final ReviewResultParser reviewResultParser;
    private final SuggestedReviewerInfoParser suggestedReviewerInfoParser;
    private final ReviewerInfoParser reviewerInfoParser;
    private final EditInfoParser editInfoParser;
    private final String id;

    public ChangeEditApiRestClient(GerritRestClient gerritRestClient,
        ChangesRestClient changesRestClient,
        ChangeApiRestClient changeApiRestClient,
        ChangesParser changesParser,
        CommentsParser commentsParser,
        IncludedInInfoParser includedInInfoParser,
        FileInfoParser fileInfoParser,
        DiffInfoParser diffInfoParser,
        AddReviewerResultParser addReviewerResultParser,
        ReviewResultParser reviewResultParser,
        SuggestedReviewerInfoParser suggestedReviewerInfoParser,
        ReviewerInfoParser reviewerInfoParser,
        EditInfoParser editInfoParser,
        String id) {
        this.gerritRestClient = gerritRestClient;
        this.changesRestClient = changesRestClient;
        this.changeApiRestClient = changeApiRestClient;
        this.changesParser = changesParser;
        this.commentsParser = commentsParser;
        this.includedInInfoParser = includedInInfoParser;
        this.fileInfoParser = fileInfoParser;
        this.diffInfoParser = diffInfoParser;
        this.addReviewerResultParser = addReviewerResultParser;
        this.reviewResultParser = reviewResultParser;
        this.suggestedReviewerInfoParser = suggestedReviewerInfoParser;
        this.reviewerInfoParser = reviewerInfoParser;
        this.editInfoParser = editInfoParser;
        this.id = id;
    }

    @Override
    public Optional<BinaryResult> getFile(String filePath) throws RestApiException {
        String request = "/changes/"+id+"/edit/"+filePath;
        try {
            HttpResponse response = gerritRestClient.request(request, null, GerritRestClient.HttpVerb.GET);
            System.out.println(response.toString());
            return Optional.of(BinaryResultUtils.createBinaryResult(response));
        } catch (IOException e) {
            throw new RestApiException("Failed to get file content.", e);
        }
    }

    @Override
    public void modifyFile(String filePath, String content) throws RestApiException {
        String request = "/changes/"+id+"/edit/"+filePath;
        System.out.println("the request is: "+request);

        try {
            HttpResponse response = gerritRestClient.request(request, content, GerritRestClient.HttpVerb.PUTT);
        } catch (IOException e) {
            throw new RestApiException("Failed to get file content.", e);
        }
    }

    @Override
    public void publish() throws RestApiException {
        String request = "/changes/"+id+"/edit:publish";
        gerritRestClient.postRequest(request,"{\"notify\":\"NONE\"}");
    }

}
