import classnames from 'classnames';
import React from 'react';
import { Container, Spinner } from 'react-bootstrap';
import './_styles.css';

export default class Loading extends React.Component<Props, State> {
    render() {
        const { message } = this.props;

        return (
            <Container fluid className='vh-100'>
                {message && <header className='message' >{message}</header>}
                <Spinner className={classnames('spinner', { message })} animation='border' />
            </Container>
        );
    }
}

type Props = {
    message?: string;
};

type State = {};