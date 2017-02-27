import React from 'react';

import Record from './record'

class History extends React.Component {
  constructor(props) {
    super(props);

    this.records = [
      {id: 1, method: 'GET', url: 'http://jsonplaceholder.typicode.com/posts/1',body: ''},
      {id: 2, method: 'POST', url: 'http://jsonplaceholder.typicode.com/posts', body: '{"userId": 1, "id": 101, "title": "foo", "body": "bar"}'},
      {id: 3, method: 'PUT', url: 'http://jsonplaceholder.typicode.com/posts/1', body: '{"userId": 1, "id": 101, "title": "foo", "body": "bar"}'},
      {id: 4, method: 'DELETE', url: 'http://jsonplaceholder.typicode.com/posts/1', body: ''},
    ];
  }

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
        {this.records.map(record =>
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
