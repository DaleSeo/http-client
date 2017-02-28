import React from 'react';

import History from './history';
import Request from './request';
import Response from './response';

class Main extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      req: {
        method: 'GET',
        path: 'http://jsonplaceholder.typicode.com/posts',
        body: '{}'
      },
      res: {
        status: '',
        body: ''
      },
      records: []
    };
  }

  componentDidMount() {
    this.updateRecords();
  }

  handleSend() {
    console.log('handleSend()');
    let req = {url: {}};
    req.method = this.state.req.method;
    req.url.path = this.state.req.path;
    req.body = this.state.req.body;

    $.post('/http/send', JSON.stringify(req))
      .done(res => {
        this.setState({res: res});

        let record = {req: req};
        $.post('/http/records', JSON.stringify(record))
          .done(res => {
            updateRecords();
          });
      });
  }

  updateRequest(req) {
    this.setState({
      req: req
    });
  }

  updateRecords() {
    console.log('updateRecords()');
    $.get('/http/records')
      .done(records => {
        console.log(records.length);
        this.setState({
          records: records.map(record => {
            return {
              id: record.id,
              method: record.request.method,
              url: record.request.url.scheme + '://' + record.request.url.host + record.request.url.path,
              body: record.request.body
            };
          })
        });
      });
  }

  render() {
    return (
      <div class="container">
        <div class="page-header">
          <h1>Http Client <small>ver 0.0.1</small></h1>
        </div>
        <div class="row">
          <div class="col-md-4">
            <History records={this.state.records} updateRequest={this.updateRequest.bind(this)} />
          </div>
          <div class="col-md-8">
            <Request
              method={this.state.req.method}
              path={this.state.req.path}
              body={this.state.req.body}
              onSend={this.handleSend.bind(this)}
              onChange={this.updateRequest.bind(this)}
            />
            <Response status={this.state.res.status} body={this.state.res.body} />
          </div>
        </div>
      </div>
    );
  }
}

export default Main;
