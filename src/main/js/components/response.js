import React from 'react';

export default class Response extends React.Component {
  render() {
    return (
      <div>
        <h2>Response</h2>
        <div class="well">
          {this.props.body}
        </div>
      </div>
    );
  };
}