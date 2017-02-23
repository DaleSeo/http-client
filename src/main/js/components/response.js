import React from 'react';

export default class Response extends React.Component {
  render() {
    let res = JSON.parse(this.props.res);
    return (
      <div id="response">
        <h2>Response</h2>
        <div class="well">
          {res.body}
        </div>
      </div>
    );
  };
}