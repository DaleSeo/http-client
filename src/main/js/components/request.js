import React from 'react';

class Request extends React.Component {

  setProp(name, event) {
    let req = {};
    req[name] = event.target.value
    this.props.onChange(req);
  }

  setMethod(event) {
    this.props.onChange({
      method: event.target.value,
      url: this.props.url,
      body: this.props.body
    });
  }

  setUrl(event) {
    this.props.onChange({
      method: this.props.method,
      url: event.target.value,
      body: this.props.body
    });
  }

  setBody(event) {
      this.props.onChange({
        method: this.props.method,
        url: this.props.url,
        body: event.target.value,
    });
  }

  handleSend(event) {
    this.props.onSend();
  }

  render() {
    return (
      <div id="request">
        <h2>Request</h2>
        <form id="formReq">
          <div class="form-group">
            <label for="method">Method</label>
            <select id="method" name="method" class="form-control" value={this.props.method} onChange={this.setMethod.bind(this)}>
              <option>GET</option>
              <option>POST</option>
              <option>PUT</option>
              <option>DELETE</option>
            </select>
          </div>
          <div class="form-group">
            <label for="url">URL</label>
            <input id="url" name="url" type="text" class="form-control" value={this.props.url} onChange={this.setUrl.bind(this)} />
          </div>
          <div class="form-group">
            <label for="body">Body</label>
            <textarea id="body" name="body" class="form-control" rows="3" value={this.props.body} onChange={this.setBody.bind(this)} />
          </div>
          <div class="text-right">
            <div class="btn-group">
              <button type="button" class="btn btn-primary" onClick={this.handleSend.bind(this)}>Send</button>
              <button type="reset" class="btn btn-default">Reset</button>
            </div>
          </div>
        </form>
      </div>
    );
  };
}

export default Request;
