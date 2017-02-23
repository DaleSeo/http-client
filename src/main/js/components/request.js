import React from 'react';

export default class Response extends React.Component {

  handleSend(event) {
    let req = {url: {}};
    req.url.path = $('#url.path').val() || 'http://jsonplaceholder.typicode.com/posts/1';
    this.props.onSend(req);
  }

  render() {
    return (
      <div id="request">
        <h2>Request</h2>
        <form id="formReq">
          <div class="form-group">
            <label for="path">Path</label>
            <input type="text" class="form-control" id="url.path" name="url.path" placeholder="Path" />
          </div>
          <button type="button" class="btn btn-default" onClick={this.handleSend.bind(this)}>Send</button>
        </form>
      </div>
    );
  };
}