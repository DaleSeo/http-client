import React from 'react';

import Record from './record'

class History extends React.Component {

  updateRequest(method, path, body) {
    this.props.updateRequest({
      method: method,
      path: path,
      body: body
    });
  }

  render() {
    return (
    <div id="history">
      <h2>History</h2>
      <div class="list-group">
        {this.props.records.map(record =>
          <Record
            key={record.id}
            method={record.method}
            url={record.url}
            onClick={this.updateRequest.bind(this, record.method, record.url, record.body)}
          />
        )}
      </div>
    </div>
    );
  };
}

export default History;
